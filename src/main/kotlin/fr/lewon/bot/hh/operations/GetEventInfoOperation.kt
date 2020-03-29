package fr.lewon.bot.hh.operations

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.operation.ResultType
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore

class GetEventInfoOperation : BotOperation("Get event info") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return ArrayList()
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        try {
            val homeContent = requestProcessor.getHomeContent(webClient, session)
            HtmlAnalyzer.INSTANCE.getEventData(homeContent)?.let {
                bot.logger.info("Event info retrieved")
                return OperationResult(true, "Retrieved event info", ResultType.OBJECT, it)
            }
            bot.logger.warn("Couldn't retrieve event info - no current event")
            return OperationResult(false, "Couldn't retrieve event info - no current event")
        } catch (e: Exception) {
            bot.logger.error("Couldn't retrieve event info", e)
        }
        return OperationResult(false, "Couldn't retrieve event info")
    }
}