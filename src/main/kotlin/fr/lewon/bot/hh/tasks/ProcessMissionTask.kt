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
        missions.sortedByDescending { it.rarity?.value }
        for (m in missions) {
            m.remainingTime?.let {
                if (it <= 0) {
                    requestProcessor.claimReward(webClient, m)
                    logger.info("Mission ${m.idMission} claimed.")
                }
            }
            if (m.isStartable) {
                requestProcessor.startMission(webClient, m)
                logger.info("Mission ${m.idMission} started. Claiming it in ${m.duration} seconds")
                return TaskResult(Delay(m.duration?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
            }
        }
            
        missions.firstOrNull { it.remainingTime != null }
                ?.let {
                    logger.info("Mission ${it.idMemberMission} running. Claiming it in ${it.duration} seconds")
                    return TaskResult(Delay(it.duration?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
                }

        requestProcessor.getFinalMissionGift(webClient)
        val nextExecution = HtmlAnalyzer.INSTANCE.getNextMissionsUpdate(activityPage)?.plus(5) ?: 10 * 60 * 60
        logger.info("Every mission finished and final gift collected. Trying again in $nextExecution seconds.")
        return TaskResult(Delay(nextExecution, TimeUnit.SECONDS))
    }
}