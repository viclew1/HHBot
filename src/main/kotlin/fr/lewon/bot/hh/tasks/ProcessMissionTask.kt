package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class ProcessMissionTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "process mission"
    }

    override fun doExecute(bot: Bot): TaskResult {
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
                    bot.logger.info("Mission ${m.idMission} claimed.")
                }
            }
            if (m.isStartable) {
                requestProcessor.startMission(webClient, session, m)
                bot.logger.info("Mission ${m.idMission} started. Claiming it in ${m.duration} seconds")
                return TaskResult(Delay(m.duration?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
            }
        }
        requestProcessor.getFinalMissionGift(webClient, session)
        bot.logger.info("Every mission finished and final gift collected. Trying again in 2 hours.")
        return TaskResult(Delay(2, TimeUnit.HOURS))
    }
}