package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class CollectLeagueRewardTask(bot: Bot) : BotTask("Collect league reward", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor
        
        val towerOfFameContent = requestProcessor.getTowerOfFameContent(webClient)
        val nextExecution = HtmlAnalyzer.INSTANCE.getEndOfLeagueTimer(towerOfFameContent)
                ?.plus(5) ?: 7 * 24 * 60 * 60
        if (HtmlAnalyzer.INSTANCE.isLeagueRewardCollectible(towerOfFameContent)) {
            val response = requestProcessor.claimLeagueRewards(webClient)
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