package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class HarvestGirlTask(bot: Bot, private val girlId: Int, initialDelayMillis: Long) : BotTask("Harvest [$girlId]", bot, initialDelayMillis) {

    @Throws(Exception::class)
    override fun doExecute(bot: Bot): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()

        val sr = requestProcessor.getSalary(webClient = webClient, session = session, which = girlId)
        sr?.time?.plus(5)?.toLong()?.let {
            bot.logger.info("Girl $girlId collected. Money made : ${sr.money}. Next harvest in $it seconds.")
            return TaskResult(Delay(it, TimeUnit.SECONDS))
        }
        bot.logger.info("Girl $girlId can't be collected. Trying again in 20 minutes")
        return TaskResult(Delay(20, TimeUnit.MINUTES))
    }

}