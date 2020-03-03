package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class CollectCompetitionRewardsTask(bot: Bot) : BotTask("Collect competitions rewards", bot) {

    override fun doExecute(bot: Bot): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val requestProcessor = HHRequestProcessor()
        val session = bot.sessionManager.getSession() as HHSession
        val activityPage = requestProcessor.getActivitiesContent(webClient, session)
        val competitionsIds = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage)
        val endedCompetitions: MutableList<Int> = ArrayList()
        for (id in competitionsIds) {
            if (requestProcessor.collectCompetitionRewards(webClient, session, id)?.success == true) {
                endedCompetitions.add(id)
            }
        }
        val secondsUntilNextCompetition = HtmlAnalyzer.INSTANCE.getSecondsUntilNextCompetition(activityPage)
        bot.logger.info("Competitions finished : $endedCompetitions. Trying again in $secondsUntilNextCompetition seconds.")
        return TaskResult(Delay(secondsUntilNextCompetition.toLong(), TimeUnit.SECONDS))
    }
}