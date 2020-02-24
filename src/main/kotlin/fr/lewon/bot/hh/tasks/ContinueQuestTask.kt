package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class ContinueQuestTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return ("Continue quest")
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        val homeContent = requestProcessor.getHomeContent(session)
//        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
//        val questId = userInfos.questing.idQuest
//        var stepCpt = 0
//        while (requestProcessor.continueQuest(session, questId).success) {
//            stepCpt++
//        }
//        runner.getBotLogger().info("Quest {} advanced {} steps. Trying again in 4 hours", questId, stepCpt)
//        return Delay(4, TimeScale.HOURS)
        return TaskResult()
    }
}