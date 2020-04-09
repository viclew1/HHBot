package fr.lewon.bot.hh.operations

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore

class GetEventInfoOperation : BotOperation("Get event info") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return ArrayList()
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor
        try {
            val homeContent = requestProcessor.getHomeContent(webClient)
            HtmlAnalyzer.INSTANCE.getEventData(homeContent)?.let {
                bot.logger.info("Event info retrieved")
                return OperationResult.ofObject(true, "Retrieved event info", it)
            }
            bot.logger.warn("Couldn't retrieve event info - no current event")
            return OperationResult(false, "Couldn't retrieve event info - no current event")
        } catch (e: Exception) {
            bot.logger.error("Couldn't retrieve event info", e)
        }
        return OperationResult(false, "Couldn't retrieve event info")
    }
}