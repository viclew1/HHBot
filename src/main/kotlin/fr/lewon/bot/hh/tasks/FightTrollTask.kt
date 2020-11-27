package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.TimeUnit

class FightTrollTask(bot: Bot) : BotTask("Fight troll", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val homeContent = requestProcessor.getHomeContent(webClient)
        val userEnergies = HtmlAnalyzer.INSTANCE.getPlayerEnergies(homeContent)
        val energy = userEnergies?.fight?.amount ?: 0
        val energyToKeep = bot.botPropertyStore.getByKey("fight_energy_to_keep") as Int
        val trollId = selectTrollIdToFight(webClient, requestProcessor, homeContent)
        if (trollId == null) {
            logger.info("No troll found. Trying again in 2 hours.")
            return TaskResult(Delay(2, TimeUnit.HOURS))
        }
        val battleTrollContent = requestProcessor.getBattleTrollContent(webClient, trollId)
        val battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent)
        battleMob?.let {
            var fightCpt = 0
            for (i in energyToKeep until energy) {
                if (requestProcessor.fightOpponentMob(webClient, battleMob)?.success == false) {
                    break
                }
                fightCpt++
            }
            logger.info("Troll $trollId fought. $fightCpt fights done. Trying again in 2 hours.")
            return TaskResult(Delay(2, TimeUnit.HOURS))
        }
        logger.info("Troll $trollId not found. Trying again in 2 hours.")
        return TaskResult(Delay(2, TimeUnit.HOURS))
    }

    private fun getCurrentWorldId(webClient: WebClient, requestProcessor: HHRequestProcessor): String? {
        val mapContent = requestProcessor.getMapContent(webClient)
        return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent)
    }

    private fun getTrollId(webClient: WebClient, requestProcessor: HHRequestProcessor, worldId: String): String? {
        val worldContent = requestProcessor.getWorldContent(webClient, worldId)
        return HtmlAnalyzer.INSTANCE.getTrollId(worldContent)
    }

    private fun selectTrollIdToFight(webClient: WebClient, requestProcessor: HHRequestProcessor, homeContent: String): String? {
        val currentTrollId = getCurrentWorldId(webClient, requestProcessor)
                ?.let { getTrollId(webClient, requestProcessor, it) }
        if (bot.botPropertyStore.getByKey("fight_troll_events") == true) {
            currentTrollId
                    ?.let { getEventTrollId(homeContent, it) }
                    ?.let { return it }
        }
        val preferredWorldId = bot.botPropertyStore.getByKey("troll_world") as Int?
        preferredWorldId?.let {
            return getTrollId(webClient, requestProcessor, it.toString())
        }
        return currentTrollId
    }

    private fun getEventTrollId(homeContent: String, currentTrollId: String): String? {
        return HtmlAnalyzer.INSTANCE.getEventData(homeContent)
                ?.girls
                ?.asSequence()
                ?.filter { it.shards != 100 }
                ?.filter { it.troll?.idTroll != null }
                ?.filter { (it.troll?.idTroll?.toLong() ?: -1) <= currentTrollId.toLong() }
                ?.sortedByDescending { it.shards }
                ?.firstOrNull()
                ?.troll
                ?.idTroll
    }

}