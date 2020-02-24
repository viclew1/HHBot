package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class FightTrollTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "Fight troll"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        val homeContent = requestProcessor.getHomeContent(session)
//        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
//        val energy = userInfos.energyFight
//        val energyToKeep = runner.getBot().getPropStore()[HHBotProperties.FIGHT_ENERGY_TO_KEEP.descriptor.key] as Int
//        val worldId = getWorldId(runner, requestProcessor, session)
//        if (worldId == null) {
//            runner.getBotLogger().info("No world found. Trying again in 1 hour.")
//            return Delay(1, TimeScale.HOURS)
//        }
//        val worldContent = requestProcessor.getWorldContent(session, worldId)
//        val trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent)
//        if (trollId == null) {
//            runner.getBotLogger().info("No troll found in world {}. Trying again in 1 hour.", worldId)
//            return Delay(1, TimeScale.HOURS)
//        }
//        val battleTrollContent = requestProcessor.getBattleTrollContent(session, trollId)
//        val battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent)
//        battleMob.idTroll = trollId
//        battleMob.idWorld = worldId
//        var fightCpt = 0
//        for (i in energyToKeep until energy!!) {
//            val response = requestProcessor.fightOpponentMob(session, battleMob)
//            if (!response.success) {
//                break
//            }
//            fightCpt++
//        }
//        runner.getBotLogger().info("Troll {} fought. {} fights done. Trying again in 2 hours.", trollId, fightCpt)
//        return Delay(2, TimeScale.HOURS)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    @Throws(Exception::class)
//    private fun getWorldId(runner: BotRunner<*>, requestProcessor: HHRequestProcessor, session: HHSession?): String? {
//        val preferedWorldId = runner.getBot().getPropStore()[HHBotProperties.TROLL_WORLD.descriptor.key] as Int
//        if (preferedWorldId != null) {
//            return preferedWorldId.toString()
//        }
//        val mapContent = requestProcessor.getMapContent(session)
//        return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent)
//    }
}