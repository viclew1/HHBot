package fr.lewon.bot.hh.operations

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
//        val id = paramsPropertyStore.getByKey(CHAMPION_ID_PARAM) as Int
//        try {
//            val session = bot.sessionManager.getSession() as HHSession
//            val championsContent = HHRequestProcessor().getChampionsMapContent(session)
//            val championPremises = HtmlAnalyzer.INSTANCE.getChampionsIds(championsContent)
//            val championPremise = championPremises!!.stream()
//                    .filter { c: ChampionPremise? -> id == c.getChampionId().toInt() }
//                    .findFirst()
//                    .orElse(null)
//                    ?: return "Invalid id, no champion found"
//        } catch (e: Exception) {
//            return "ERROR : " + e.message
//        }ab
//        runner.addAction(ChampionFightOperation(id))
//        return "Now fighting champion $id"
        return OperationResult(false, null, null)
    }
}