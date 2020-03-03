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

    override fun doExecute(bot: Bot): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val requestProcessor = HHRequestProcessor()
        val session = bot.sessionManager.getSession() as HHSession
        requestProcessor.getArenaContent(webClient, session)
        var cpt = 0
        for (i in 0..2) {
            val pageContent = requestProcessor.getBattleArenaContent(webClient, session, i)
            val battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(pageContent) ?: continue
            if (requestProcessor.fightOpponentPlayer(webClient, session, battlePlayer)?.success == true) {
                cpt++
            }
        }
        bot.logger.info("$cpt arena fights done. Trying again in 15 minutes.")
        return TaskResult(Delay(15, TimeUnit.MINUTES))
    }
}