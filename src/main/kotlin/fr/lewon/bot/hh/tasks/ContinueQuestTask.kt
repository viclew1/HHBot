package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class ContinueQuestTask(bot: Bot) : BotTask("Continue quest", bot) {

    override fun doExecute(): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val homeContent = requestProcessor.getHomeContent(webClient, session)
        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        userInfos?.questing?.idQuest?.let {
            var stepCpt = 0
            while (requestProcessor.continueQuest(webClient, session, it)?.success == true) {
                stepCpt++
            }
            logger.info("Quest $it advanced $stepCpt steps. Trying again in 4 hours")
        }
        return TaskResult(Delay(4, TimeUnit.HOURS))
    }
}