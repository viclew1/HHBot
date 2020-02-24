package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class ProcessMissionTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "process mission"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        val activityPage = requestProcessor.getActivitiesContent(session)
//        val missions = HtmlAnalyzer.INSTANCE.getMissions(activityPage)
//        missions.sort(java.util.Comparator { m1: Mission, m2: Mission -> m2.rarity.value - m1.rarity.value })
//        for (m in missions!!) {
//            if (m.remainingTime != null && m.remainingTime <= 0) {
//                requestProcessor.claimReward(session, m)
//                runner.getBotLogger().info("Mission {} claimed.", m.idMission)
//            } else if (m!!.isStartable) {
//                requestProcessor.startMission(session, m)
//                runner.getBotLogger().info("Mission {} started. Claiming it in {} seconds", m.idMission, m.duration)
//                return Delay(m.duration + 5, TimeScale.SECONDS)
//            }
//        }
//        runner.getBotLogger().info("Every mission finished. Trying again in 2 hours.")
//        requestProcessor.getFinalMissionGift(session)
//        return Delay(2, TimeScale.HOURS)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}