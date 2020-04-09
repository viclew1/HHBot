package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class HarvestGirlTask(bot: Bot, private val girlId: Int, initialDelayMillis: Long) : BotTask("Harvest [$girlId]", bot, initialDelayMillis) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val sr = requestProcessor.getSalary(webClient = webClient, which = girlId)
        sr?.time?.plus(5)?.toLong()?.let {
            logger.info("Girl $girlId collected. Money made : ${sr.money}. Next harvest in $it seconds.")
            return TaskResult(Delay(it, TimeUnit.SECONDS))
        }
        logger.info("Girl $girlId can't be collected. Trying again in 20 minutes")
        return TaskResult(Delay(20, TimeUnit.MINUTES))
    }

}