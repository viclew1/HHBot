package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class CollectWeeklyRewardTask(bot: Bot) : BotTask("Collect weekly reward", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = HHRequestProcessor()
        val towerOfFameContent = requestProcessor.getTowerOfFameContent(webClient, session)
        val nextExecution = HtmlAnalyzer.INSTANCE.getEndOfWeekTimer(towerOfFameContent)
                ?.plus(5) ?: 7 * 24 * 60 * 60
        HtmlAnalyzer.INSTANCE.getRewardsVar(towerOfFameContent)
                ?.takeIf { it == 1 }
                ?.let {
                    val response = requestProcessor.claimWeeklyRewards(webClient, session)
                    if (response?.success == true) {
                        logger.info("Claimed weekly rewards successfully")
                    } else {
                        logger.error("Failed to claim weekly rewards : ${response?.error}")
                    }
                }
        logger.info("Claiming next weekly rewards in $nextExecution seconds")
        return TaskResult(Delay(nextExecution, TimeUnit.SECONDS))
    }

}