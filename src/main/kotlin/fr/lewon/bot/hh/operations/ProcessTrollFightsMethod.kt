package fr.lewon.web.bot.methods

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.props.BotPropertyType

class ProcessTrollFightsMethod : BotOperation("Process troll fight") {

    companion object {
        private const val TROLL_WORLD_PARAM = "troll_world"
        private const val FIGHTS_COUNT_PARAM = "fights_count"
    }

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return listOf(
                BotPropertyDescriptor(TROLL_WORLD_PARAM, BotPropertyType.INTEGER, null, "World in which the troll will be fought", isNeeded = true, isNullable = false),
                BotPropertyDescriptor(FIGHTS_COUNT_PARAM, BotPropertyType.INTEGER, null, "Number of fights to process. Will fight all you can if null", isNeeded = true, isNullable = true)
        )
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
//        val trollWorldVal = params[TROLL_WORLD_PARAM]
//        val fightsCountVal = params[FIGHTS_COUNT_PARAM]
//        if (trollWorldVal == null || fightsCountVal == null) {
//            throw InvalidValueParameterException(TROLL_WORLD_PARAM, trollWorldVal)
//        }
        return OperationResult(false, null, null)
    }
}