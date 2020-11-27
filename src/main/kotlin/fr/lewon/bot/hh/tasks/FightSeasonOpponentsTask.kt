package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class FightSeasonOpponentsTask(bot: Bot) : BotTask("Fight season opponents", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val seasonContent = requestProcessor.getSeasonContent(webClient)
        if (HtmlAnalyzer.INSTANCE.getSeasonId(seasonContent) == null) {
            logger.info("No season found, trying again in 12 hours")
            return TaskResult(Delay(12, TimeUnit.HOURS))
        }

        val homeContent = requestProcessor.getHomeContent(webClient)
        val userEnergies = HtmlAnalyzer.INSTANCE.getPlayerEnergies(homeContent)
        val energy = userEnergies?.kiss?.amount ?: 0
        if (energy == 0) {
            logger.info("No kiss energy for season fights. Trying again in 3 hours")
            return TaskResult(Delay(3, TimeUnit.HOURS))
        }

        for (i in 0 until energy) {
            val seasonArenaContent = requestProcessor.getSeasonArenaContent(webClient)
            val opponentsIds = HtmlAnalyzer.INSTANCE.getSeasonArenaOpponentIds(seasonArenaContent)
            if (opponentsIds.isEmpty()) {
                logger.info("No season arena opponent found. Trying again in 3 hours")
                return TaskResult(Delay(3, TimeUnit.HOURS))
            }
            val battleContent = requestProcessor.getBattleSeasonArenaContent(webClient, opponentsIds[0])
            val battlePlayerSeason = HtmlAnalyzer.INSTANCE.findOpponentBattleSeason(battleContent)
            if (battlePlayerSeason == null) {
                logger.info("Failed to retrieve player [${opponentsIds[0]}] data. Trying again in 3 hours")
                return TaskResult(Delay(3, TimeUnit.HOURS))
            }
            requestProcessor.fightOpponentSeason(webClient, battlePlayerSeason)
        }

        logger.info("$energy season fights done. Trying again in 5 hours")
        return TaskResult(Delay(5, TimeUnit.HOURS))
    }
}