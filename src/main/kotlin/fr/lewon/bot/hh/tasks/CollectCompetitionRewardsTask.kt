package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class CollectCompetitionRewardsTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "Collect competitions rewards"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        val activityPage = requestProcessor.getActivitiesContent(session)
//        val competitionsIds = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage)
//        val endedCompetitions: MutableList<Int?> = ArrayList()
//        for (id in competitionsIds!!) {
//            if (requestProcessor.collectCompetitionRewards(id, session).success) {
//                endedCompetitions.add(id)
//            }
//        }
//        val secondsUntilNextCompetition = HtmlAnalyzer.INSTANCE.getSecondsUntilNextCompetition(activityPage)
//        runner.getBotLogger().info("Competitions finished : {}. Trying again in {} seconds.", endedCompetitions, secondsUntilNextCompetition)
//        return Delay(secondsUntilNextCompetition, TimeScale.SECONDS)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}