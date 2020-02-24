package fr.lewon.bot.hh.operations

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.props.BotPropertyType

class UpgradeStatMethod : BotOperation("Upgrade stat") {

    companion object {
        private const val UPGRADE_COUNT_KEY = "upgrade_count"
        private const val STAT_TO_UPGRADE_KEY = "stat_to_upgrade"
    }

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return listOf(
                BotPropertyDescriptor(UPGRADE_COUNT_KEY, BotPropertyType.INTEGER, 1, "Number of times to upgrade the property", true, false),
                BotPropertyDescriptor(STAT_TO_UPGRADE_KEY, BotPropertyType.INTEGER, 1, "Stat to upgrade", true, false)
        )
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
//        val maxUpgradeCount = paramsPropertyStore.getByKey(UPGRADE_COUNT_KEY) as Int
//        val statToUpgrade = paramsPropertyStore.getByKey(STAT_TO_UPGRADE_KEY) as Int
//        var cpt = 0
//        var requestProcessor = HHRequestProcessor()
//        try {
//            while (cpt < maxUpgradeCount && requestProcessor.upgradeStat(bot.sessionManager.getWebClient(), bot.sessionManager.getSession(), statToUpgrade).success) {
//                cpt++
//            }
//        } catch (e: Exception) {
//            runner.getBotLogger().error("Upgrade failed", e)
//        }
//        return "Stat $statToUpgrade upgraded $cpt times"
        return OperationResult(false, null, null)
    }
}