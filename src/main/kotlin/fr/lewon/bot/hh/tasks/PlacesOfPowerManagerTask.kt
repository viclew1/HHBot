package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class PlacesOfPowerManagerTask(bot: Bot) : BotTask("process mission", bot) {

    private val treatedPlacesOfPower: MutableList<Int> = ArrayList()

    override fun doExecute(): TaskResult {
        val newPopTasks: MutableList<BotTask> = ArrayList()
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val activityPage = requestProcessor.getActivitiesContent(webClient)
        val newPop = HtmlAnalyzer.INSTANCE.getPlacesOfPower(activityPage)
                .map { it.idPlaceOfPower ?: -1 }
                .filter { it >= 0 && !treatedPlacesOfPower.contains(it) }

        for (pop in newPop) {
            newPopTasks.add(ProcessPlaceOfPowerTask(pop, bot))
        }

        return TaskResult(Delay(3, TimeUnit.HOURS), newPopTasks)
    }
}