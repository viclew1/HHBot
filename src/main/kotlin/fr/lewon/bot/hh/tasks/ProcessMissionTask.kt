package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class ProcessMissionTask(bot: Bot) : BotTask("process mission", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val activityPage = requestProcessor.getActivitiesContent(webClient)
        val missions = HtmlAnalyzer.INSTANCE.getMissions(activityPage)

        missions.firstOrNull { it.remainingTime?.compareTo(0) ?: -1 > 0 }
                ?.let {
                    logger.info("Mission ${it.idMemberMission} running. Claiming it in ${it.remainingTime} seconds")
                    return TaskResult(Delay(it.remainingTime?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
                }

        missions.filter { it.remainingTime?.compareTo(0) ?: 1 <= 0 }
                .forEach {
                    requestProcessor.claimReward(webClient, it)
                    logger.info("Mission ${it.idMission} claimed.")
                }

        missions.firstOrNull { it.remainingTime == null }
                ?.let {
                    requestProcessor.startMission(webClient, it)
                    logger.info("Mission ${it.idMission} started. Claiming it in ${it.duration} seconds")
                    return TaskResult(Delay(it.duration?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
                }

        requestProcessor.getFinalMissionGift(webClient)
        val nextExecution = HtmlAnalyzer.INSTANCE.getNextMissionsUpdate(activityPage)?.plus(5) ?: 10 * 60 * 60
        logger.info("Every mission finished and final gift collected. Trying again in $nextExecution seconds.")
        return TaskResult(Delay(nextExecution, TimeUnit.SECONDS))
    }
}