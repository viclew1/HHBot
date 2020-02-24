package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class FightTowerOfFameOpponentsTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "Fight tower of fame opponents"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val session = sessionManager.getSession(requestProcessor)
//        val homeContent = requestProcessor.getHomeContent(session)
//        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
//        val energy = userInfos.energyChallenge
//        val energyToKeep = runner.getBot().getPropStore()[HHBotProperties.TOWER_ENERGY_TO_KEEP.descriptor.key] as Int
//        val fightCount = energy!! - energyToKeep
//        if (fightCount <= 0) {
//            runner.getBotLogger().info("Not enough energy. Trying again in 2 hour")
//            return Delay(2, TimeScale.HOURS)
//        }
//        val pageContent = requestProcessor.getTowerOfFameContent(session)
//        val premises = HtmlAnalyzer.INSTANCE.findHallOfFameOpponents(pageContent).stream()
//                .filter { p: TowerOfFameOpponentPremise? -> p.getNbChallengesPlayed() < 3 }
//                .sorted { o1: TowerOfFameOpponentPremise?, o2: TowerOfFameOpponentPremise? -> o1.getLvl() - o2.getLvl() }
//                .collect(Collectors.toList())
//        var cpt = 0
//        for (premise in premises) {
//            val battlePageContent = requestProcessor.getLeagueBattleContent(session, premise.id)
//            val battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(battlePageContent) ?: continue
//            var i = 0
//            while (i++ < 3 && requestProcessor.fightOpponentPlayer(session, battlePlayer).success) {
//                if (++cpt >= fightCount) {
//                    runner.getBotLogger().info("{} Tower of fame fights done. Trying again in 2 hours", cpt)
//                    return Delay(2, TimeScale.HOURS)
//                }
//            }
//        }
//        runner.getBotLogger().info("No opponent to fight. Trying again in 5 hours")
//        return Delay(5, TimeScale.HOURS)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}