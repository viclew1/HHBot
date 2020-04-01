package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class ProcessMissionTask(bot: Bot) : BotTask("process mission", bot) {

    override fun doExecute(): TaskResult {
        val requestProcessor = HHRequestProcessor()
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val activityPage = requestProcessor.getActivitiesContent(webClient, session)
        val missions = HtmlAnalyzer.INSTANCE.getMissions(activityPage)
        missions.sortedByDescending { it.rarity?.value }
        for (m in missions) {
            m.remainingTime?.let {
                if (it <= 0) {
                    requestProcessor.claimReward(webClient, session, m)
                    logger.info("Mission ${m.idMission} claimed.")
                }
            }
            if (m.isStartable) {
                requestProcessor.startMission(webClient, session, m)
                logger.info("Mission ${m.idMission} started. Claiming it in ${m.duration} seconds")
                return TaskResult(Delay(m.duration?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
            }
        }
        requestProcessor.getFinalMissionGift(webClient, session)
        val nextExecution = HtmlAnalyzer.INSTANCE.getNextMissionsUpdate(activityPage)?.plus(5) ?: 10 * 60 * 60
        logger.info("Every mission finished and final gift collected. Trying again in $nextExecution seconds.")
        return TaskResult(Delay(nextExecution, TimeUnit.SECONDS))
    }
}