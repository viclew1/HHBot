package fr.lewon.bot.hh.operations

import fr.lewon.bot.hh.entities.champions.ChampionPremise
import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.hh.tasks.ChampionFightTask
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.props.BotPropertyType

class FightChampionMethod : BotOperation("Fight champion") {

    companion object {
        private const val CHAMPION_ID_PARAM = "CHAMPION_ID"
    }

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return listOf(BotPropertyDescriptor(CHAMPION_ID_PARAM, BotPropertyType.INTEGER, null, "Id of the champion", isNeeded = true, isNullable = false))
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        val id = paramsPropertyStore.getByKey(CHAMPION_ID_PARAM) as Int
        try {
            val session = bot.sessionManager.getSession() as HHSession
            val championsContent = HHRequestProcessor().getChampionsMapContent(bot.sessionManager.getWebClient(), session)
            val championPremises = HtmlAnalyzer.INSTANCE.getChampionsIds(championsContent)
            val championPremise = championPremises.stream()
                    .filter { c: ChampionPremise -> id == c.championId }
                    .findFirst()
                    .orElse(null)
                    ?: return OperationResult(false, "Invalid id, no champion found")
        } catch (e: Exception) {
            bot.logger.error("Couldn't retrieve champion info")
            return OperationResult(false, "Couldn't retrieve champion info")
        }
        bot.startTask(ChampionFightTask(bot, id))
        return OperationResult(true, "Now fighting champion $id")
    }
}