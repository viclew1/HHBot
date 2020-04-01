package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class FightTowerOfFameOpponentsTask(bot: Bot) : BotTask("Fight tower of fame opponents", bot) {

    override fun doExecute(): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val homeContent = requestProcessor.getHomeContent(webClient, session)
        val userInfo = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        val energy = userInfo?.energyChallenge ?: 0
        val energyToKeep = bot.botPropertyStore.getByKey("tower_energy_to_keep") as Int
        val fightCount = energy - energyToKeep
        if (fightCount <= 0) {
            logger.info("Not enough energy. Trying again in 2 hour")
            return TaskResult(Delay(2, TimeUnit.HOURS))
        }
        val pageContent = requestProcessor.getTowerOfFameContent(webClient, session)
        val premises = HtmlAnalyzer.INSTANCE.findHallOfFameOpponents(pageContent)
                .filter { it.nbChallengesPlayed < 3 }
                .sortedBy { it.lvl }
        var cpt = 0
        for (premise in premises) {
            val battlePageContent = premise.id?.let { requestProcessor.getLeagueBattleContent(webClient, session, it) }
                    ?: ""
            val battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(battlePageContent) ?: continue
            var i = premise.nbChallengesPlayed
            while (i++ < 3 && requestProcessor.fightOpponentPlayer(webClient, session, battlePlayer)?.success == true) {
                if (++cpt >= fightCount) {
                    logger.info("$cpt Tower of fame fights done. Trying again in 2 hours")
                    return TaskResult(Delay(2, TimeUnit.HOURS))
                }
            }
        }
        logger.info("No opponent to fight. Trying again in 5 hours")
        return TaskResult(Delay(5, TimeUnit.HOURS))
    }
}