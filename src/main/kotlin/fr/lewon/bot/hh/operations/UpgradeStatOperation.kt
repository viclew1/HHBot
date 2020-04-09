package fr.lewon.bot.hh.operations

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.operation.BotOperation
import fr.lewon.bot.runner.bot.operation.OperationResult
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyStore
import fr.lewon.bot.runner.bot.props.BotPropertyType

class UpgradeStatOperation : BotOperation("Upgrade stat") {

    companion object {
        private const val UPGRADE_COUNT_KEY = "upgrade_count"
        private const val STAT_TO_UPGRADE_KEY = "stat_to_upgrade"
    }

    override fun getNeededProperties(bot: Bot): List<BotPropertyDescriptor> {
        return listOf(
                BotPropertyDescriptor(UPGRADE_COUNT_KEY, BotPropertyType.INTEGER, 1, "Number of times to upgrade the property", isNeeded = true, isNullable = false),
                BotPropertyDescriptor(STAT_TO_UPGRADE_KEY, BotPropertyType.INTEGER, 1, "Stat to upgrade", isNeeded = true, isNullable = false)
        )
    }

    override fun run(bot: Bot, paramsPropertyStore: BotPropertyStore): OperationResult {
        val maxUpgradeCount = paramsPropertyStore.getByKey(UPGRADE_COUNT_KEY) as Int
        val statToUpgrade = paramsPropertyStore.getByKey(STAT_TO_UPGRADE_KEY) as Int
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        var cpt = 0
        try {
            while (cpt < maxUpgradeCount && requestProcessor.upgradeStat(webClient, statToUpgrade)?.success == true) {
                cpt++
            }
        } catch (e: Exception) {
            bot.logger.error("Upgrade failed", e)
            return OperationResult(false, "Upgrade failed")
        }
        bot.logger.info("Stat $statToUpgrade upgraded $cpt times")
        return OperationResult(true, "Stat $statToUpgrade upgraded $cpt times")
    }
}