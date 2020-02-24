package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.enums.Currency
import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

class ChampionFightTask(bot: Bot, private val championId: Int) : BotTask(bot) {

    override fun getLabel(): String {
        return "Champion fight"
    }

    override fun doExecute(bot: Bot): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val championContent = requestProcessor.getChampionPageContent(webClient, session, championId)
        val championData = HtmlAnalyzer.INSTANCE.getChampionData(championContent)
        championData?.champion?.currentTickets?.let {
            if (it == 0) {
                bot.logger.info("No ticket left, can't fight champion ${championId}.")
                return TaskResult()
            }
        }
        var teamIds = championData?.team?.stream()
                ?.map { g -> g.id }
                ?.collect(Collectors.toList()) ?: emptyList<Int>()
        val battleResp = requestProcessor.fightChampion(webClient, session, Currency.TICKET, championId, teamIds)
        if (battleResp?.success != true) {
            bot.logger.info("Can't fight champion ${championId}.")
            return TaskResult()
        }
        battleResp.fnl?.attackerEgo?.let {
            if (it <= 0) {
                bot.logger.info("Lost against champion ${championId}. Trying again in 15 minutes")
                return TaskResult(Delay(15, TimeUnit.MINUTES))
            }
        }
        bot.logger.info("Won against champion ${championId}.")
        return TaskResult()
    }

}