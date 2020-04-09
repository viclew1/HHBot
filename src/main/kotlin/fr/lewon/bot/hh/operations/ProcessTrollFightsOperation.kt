package fr.lewon.web.bot.methods

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.props.BotPropertyType
import kotlin.math.min

class ProcessTrollFightsOperation : BotOperation("Process troll fight") {

    companion object {
        private const val TROLL_WORLD_PARAM = "troll_world"
        private const val FIGHTS_COUNT_PARAM = "fights_count"
    }

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return listOf(
                BotPropertyDescriptor(TROLL_WORLD_PARAM, BotPropertyType.INTEGER, null, "World in which the troll will be fought", isNeeded = true, isNullable = false),
                BotPropertyDescriptor(FIGHTS_COUNT_PARAM, BotPropertyType.INTEGER, null, "Number of fights to process. Will fight all you can if null", isNeeded = true, isNullable = false)
        )
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        val worldId = paramsPropertyStore.getByKey(TROLL_WORLD_PARAM) as Int
        val fightsCountVal = paramsPropertyStore.getByKey(FIGHTS_COUNT_PARAM) as Int
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val homeContent = requestProcessor.getHomeContent(webClient)
        val userInfo = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        val energy = userInfo?.energyFight ?: 0

        if (energy == 0) {
            bot.logger.info("No energy left to fight trolls")
            return OperationResult(false, "No energy left to fight trolls")
        }

        if (fightsCountVal <= 0) {
            bot.logger.info("Invalid amount of fights, should be > 0")
            return OperationResult(false, "Invalid amount of fights, should be > 0")
        }

        val worldContent = requestProcessor.getWorldContent(webClient, worldId.toString())
        val trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent)
        if (trollId == null) {
            bot.logger.info("No troll found in world $worldId.")
            return OperationResult(false, "No troll found in world $worldId.")
        }
        val battleTrollContent = requestProcessor.getBattleTrollContent(webClient, trollId)
        val battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent)
        battleMob?.let {
            battleMob.idTroll = trollId
            battleMob.idWorld = worldId.toString()
            var fightCpt = 0
            for (i in 0 until min(energy, fightsCountVal)) {
                if (requestProcessor.fightOpponentMob(webClient, battleMob)?.success == false) {
                    break
                }
                fightCpt++
            }
            bot.logger.info("Troll $trollId fought. $fightCpt fights done.")
            return OperationResult(true, "Troll $trollId fought. $fightCpt fights done.")
        }
        bot.logger.info("No troll found in world $worldId.")
        return OperationResult(false, "No troll found in world $worldId.")
    }
}