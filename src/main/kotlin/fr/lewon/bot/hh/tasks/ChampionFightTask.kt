package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class ChampionFightTask(bot: Bot, private val championId: Int) : BotTask(bot) {

    override fun getLabel(): String {
        return "Champion fight"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        val championContent = requestProcessor.getChampionPageContent(session, Integer.valueOf(championId))
//        val championData = HtmlAnalyzer.INSTANCE.getChampionData(championContent)
//        if (championData.champion.currentTickets.toInt() == 0) {
//            runner.botLogger.info("No ticket left, can't fight champion {}. Trying again in 4 hours", *arrayOf<Any>(Integer.valueOf(championId)))
//            return Delay(Integer.valueOf(4), TimeScale.HOURS)
//        }
//        val teamIds = championData.team
//                .stream()
//                .map { obj: Girl? -> obj.getId() }
//                .collect(Collectors.toList())
//        val battleResp = requestProcessor.fightChampion(session, Currency.TICKET, championId, teamIds)
//        if (!battleResp.success.toBoolean()) {
//            runner.botLogger.info("Can't fight champion {}. Trying again in 4 hours", championId)
//            return Delay(Integer.valueOf(4), TimeScale.HOURS)
//        }
//        if (battleResp.fnl.attackerEgo.toInt() <= 0) {
//            runner.botLogger.info("Lost against champion {}. Trying again in 15 minutes", championId)
//            return Delay(Integer.valueOf(15), TimeScale.MINUTES)
//        }
//        runner.botLogger.info("Won against champion {}.", championId)
//        return null
        return TaskResult()
    }

}