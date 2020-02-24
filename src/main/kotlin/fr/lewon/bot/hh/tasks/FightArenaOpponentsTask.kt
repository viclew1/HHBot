package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class FightArenaOpponentsTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "Fight arena opponents"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        requestProcessor.getArenaContent(session)
//        var cpt = 0
//        for (i in 0..2) {
//            val pageContent = requestProcessor.getBattleArenaContent(session, i)
//            val battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(pageContent) ?: continue
//            if (requestProcessor.fightOpponentPlayer(session, battlePlayer).success) {
//                cpt++
//            }
//        }
//        runner.getBotLogger().info("{} arena fights done. Trying again in 15 minutes.", cpt)
//        return Delay(15, TimeScale.MINUTES)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}