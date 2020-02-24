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

class FightTrollTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "Fight troll"
    }

    override fun doExecute(bot: Bot): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val homeContent = requestProcessor.getHomeContent(webClient, session)
        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        val energy = userInfos?.energyFight ?: 0
        val energyToKeep = bot.botPropertyStore.getByKey("fight_energy_to_keep") as Int
        val worldId = getWorldId(bot, requestProcessor, webClient, session)
        if (worldId == null) {
            bot.logger.info("No world found. Trying again in 1 hour.")
            return TaskResult(Delay(1, TimeUnit.HOURS))
        }
        val worldContent = requestProcessor.getWorldContent(webClient, session, worldId)
        val trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent)
        if (trollId == null) {
            bot.logger.info("No troll found in world $worldId. Trying again in 1 hour.")
            return TaskResult(Delay(1, TimeUnit.HOURS))
        }
        val battleTrollContent = requestProcessor.getBattleTrollContent(webClient, session, trollId)
        val battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent)
        battleMob?.let {
            battleMob.idTroll = trollId
            battleMob.idWorld = worldId
            var fightCpt = 0
            for (i in energyToKeep until energy) {
                if (requestProcessor.fightOpponentMob(webClient, session, battleMob)?.success == false) {
                    break
                }
                fightCpt++
            }
            bot.logger.info("Troll $trollId fought. $fightCpt fights done. Trying again in 2 hours.")
            return TaskResult(Delay(2, TimeUnit.HOURS))
        }
        bot.logger.info("No troll found in world $worldId. Trying again in 1 hour.")
        return TaskResult(Delay(1, TimeUnit.HOURS))
    }

    @Throws(Exception::class)
    private fun getWorldId(bot: Bot, requestProcessor: HHRequestProcessor, webClient: WebClient, session: HHSession): String? {
        val preferredWorldId = bot.botPropertyStore.getByKey("troll_world") as Int?
        preferredWorldId?.let { return it.toString() }
        val mapContent = requestProcessor.getMapContent(webClient, session)
        return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent)
    }
}