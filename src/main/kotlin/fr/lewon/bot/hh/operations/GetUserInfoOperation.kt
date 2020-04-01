package fr.lewon.bot.hh.operations

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore


class GetUserInfoOperation : BotOperation("Get user info") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return ArrayList()
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        try {
            val homeContent = requestProcessor.getHomeContent(webClient, session)
            HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)?.let {
                return OperationResult.ofObject(true, "Retrieved user info", it)
            }
        } catch (e: Exception) {
            bot.logger.error("Couldn't retrieve user info", e)
        }
        return OperationResult(false, "Couldn't retrieve user info")
    }
}