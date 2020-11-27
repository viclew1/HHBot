package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class ContinueQuestTask(bot: Bot) : BotTask("Continue quest", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val homeContent = requestProcessor.getHomeContent(webClient)
        var userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        var userEnergies = HtmlAnalyzer.INSTANCE.getPlayerEnergies(homeContent)
        userInfos?.questing?.idQuest?.let {
            var stepCpt = 0
            while (requestProcessor.continueQuest(webClient, it)?.success == true) {
                stepCpt++
            }
            logger.info("Quest $it advanced $stepCpt steps. Trying again in 4 hours")
        }

        userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        logger.info("Quest energy spent. Quest energy left : ${userEnergies?.quest?.amount}")

        return TaskResult(Delay(4, TimeUnit.HOURS))
    }
}