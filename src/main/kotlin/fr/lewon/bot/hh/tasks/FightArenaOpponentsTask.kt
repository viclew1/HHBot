package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class FightArenaOpponentsTask(bot: Bot) : BotTask("Fight arena opponents", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = HHRequestProcessor()
        val arenaContent = requestProcessor.getArenaContent(webClient, session)
        for (i in 0..2) {
            val pageContent = requestProcessor.getBattleArenaContent(webClient, session, i)
            val battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(pageContent) ?: continue
            requestProcessor.fightOpponentPlayer(webClient, session, battlePlayer)
        }
        val nextExec = (HtmlAnalyzer.INSTANCE.getNextArenaReset(arenaContent) ?: 30 * 60) + 5
        logger.info("Arena fights done. Trying again in $nextExec seconds")
        return TaskResult(Delay(nextExec, TimeUnit.SECONDS))
    }
}