package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class CollectCompetitionRewardsTask(bot: Bot) : BotTask("Collect competitions rewards", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val activityPage = requestProcessor.getActivitiesContent(webClient)
        val competitionsIds = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage)
        val endedCompetitions: MutableList<Int> = ArrayList()
        for (id in competitionsIds) {
            if (requestProcessor.collectCompetitionRewards(webClient, id)?.success == true) {
                endedCompetitions.add(id)
            }
        }
        val secondsUntilNextCompetition = HtmlAnalyzer.INSTANCE.getSecondsUntilNextCompetition(activityPage) + 5
        logger.info("Competitions finished : $endedCompetitions. Trying again in $secondsUntilNextCompetition seconds.")
        return TaskResult(Delay(secondsUntilNextCompetition.toLong(), TimeUnit.SECONDS))
    }
}