package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.girls.Girl
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class ProcessPlaceOfPowerTask(private val idPlaceOfPower: Int, bot: Bot) : BotTask("process mission", bot) {

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val activityPage = requestProcessor.getActivitiesContent(webClient)
        val pop = HtmlAnalyzer.INSTANCE.getPlacesOfPower(activityPage)
                .firstOrNull { it.idPlaceOfPower == idPlaceOfPower }

        if (pop == null) {
            logger.error("Place of power [$idPlaceOfPower] not found. Trying again in 3 hours")
            return TaskResult(Delay(3, TimeUnit.HOURS))
        }

        if (pop.remainingTime ?: Long.MAX_VALUE <= 0) {
            requestProcessor.collectPlaceOfPower(webClient, idPlaceOfPower)
            logger.info("Place of power [$idPlaceOfPower] collected. Starting it again in 5 seconds.")
            return TaskResult(Delay(5, TimeUnit.SECONDS))
        }
        if (pop.remainingTime != null) {
            requestProcessor.collectPlaceOfPower(webClient, idPlaceOfPower)
            val remaining = pop.remainingTime ?: 36000
            logger.info("Place of power [$idPlaceOfPower] running. Collecting it in $remaining seconds.")
            return TaskResult(Delay(remaining + 5, TimeUnit.SECONDS))
        }

        val maxPower = pop.maxTeamPower
        if (maxPower == null) {
            logger.error("Place of power [$idPlaceOfPower] misses a max team power. Trying again in 3 hours")
            return TaskResult(Delay(3, TimeUnit.HOURS))
        }

        val girls = ArrayList<Int>()
        var power = 0
        for (girl in pop.girls) {
            girls.add(girl.id)
            power += getGirlPower(girl, pop.girlClass)
            if (power > maxPower) {
                break
            }
        }
        requestProcessor.startPlaceOfPower(webClient, idPlaceOfPower, girls)
        logger.info("Place of power [$idPlaceOfPower] started.")
        return TaskResult(Delay(5, TimeUnit.SECONDS))
    }

    private fun getGirlPower(girl: Girl, carac: Int?): Int {
        return when (carac) {
            1 -> girl.carac1 ?: 0.0
            2 -> girl.carac2 ?: 0.0
            3 -> girl.carac3 ?: 0.0
            else -> 0.0
        }.toInt()
    }
}