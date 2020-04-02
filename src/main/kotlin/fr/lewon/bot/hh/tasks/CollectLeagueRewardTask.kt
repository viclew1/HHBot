package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class CollectLeagueRewardTask(bot: Bot) : BotTask("Collect league reward", bot) {

    override fun doExecute(): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val towerOfFameContent = requestProcessor.getTowerOfFameContent(webClient, session)
        val nextExecution = HtmlAnalyzer.INSTANCE.getEndOfLeagueTimer(towerOfFameContent)
                ?.plus(5) ?: 7 * 24 * 60 * 60
        if (HtmlAnalyzer.INSTANCE.isLeagueRewardCollectible(towerOfFameContent)) {
            val response = requestProcessor.claimLeagueRewards(webClient, session)
            if (response?.success == true) {
                logger.info("Claimed league rewards successfully")
            } else {
                logger.error("Failed to claim league rewards : ${response?.error}")
            }
        }
        logger.info("Claiming next league rewards in $nextExecution seconds")
        return TaskResult(Delay(nextExecution, TimeUnit.SECONDS))
    }

}