package fr.lewon.bot.hh.operations

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore


class GetUserInfosMethod : BotOperation("Get user infos") {

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return ArrayList()
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
//        return try {
//            val homeContent = requestProcessor.getHomeContent(sessionManager.getSession(requestProcessor))
//            HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)!!
//        } catch (e: Exception) {
//            e.message!!
//        }
        return OperationResult(false, null, null)
    }
}