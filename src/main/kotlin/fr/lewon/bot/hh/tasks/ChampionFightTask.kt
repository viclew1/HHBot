package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.enums.Currency
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

class ChampionFightTask(bot: Bot, private val championId: Int) : BotTask("Champion fight", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val championContent = requestProcessor.getChampionPageContent(webClient, championId)
        val championData = HtmlAnalyzer.INSTANCE.getChampionData(championContent)

        championData?.champion?.currentTickets?.let {
            if (it == 0) {
                logger.info("No ticket left, can't fight champion ${championId}.")
                updateChampionFightProperty(bot, false)
                return TaskResult()
            }
        }
        val teamIds = championData?.team?.stream()
                ?.map { g -> g.id }
                ?.collect(Collectors.toList()) ?: emptyList<Int>()
        val battleResp = requestProcessor.fightChampion(webClient, Currency.TICKET, championId, teamIds)
        if (battleResp?.success != true) {
            logger.info("Can't fight champion ${championId}.")
            updateChampionFightProperty(bot, false)
            return TaskResult()
        }
        battleResp.fnl?.attackerEgo?.let {
            if (it <= 0) {
                logger.info("Lost against champion ${championId}. Trying again in 15 minutes")
                return TaskResult(Delay(15, TimeUnit.MINUTES))
            }
        }
        logger.info("Won against champion ${championId}.")
        updateChampionFightProperty(bot, false)
        return TaskResult()
    }

    private fun updateChampionFightProperty(bot: Bot, value: Boolean) {
        bot.sharedProperties["CHAMPION_$championId"] = value
    }

}