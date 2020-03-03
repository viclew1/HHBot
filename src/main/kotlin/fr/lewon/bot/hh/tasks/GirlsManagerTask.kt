package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.girls.Girl
import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

class GirlsManagerTask(bot: Bot) : BotTask("Girls manager", bot) {

    override fun doExecute(bot: Bot): TaskResult {

        val newTasks: MutableList<BotTask> = mutableListOf()
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val haremContent = requestProcessor.getHaremContent(webClient, session)

        val ownedGirls = HtmlAnalyzer.INSTANCE.findAllGirls(haremContent).stream()
                .filter { obj: Girl -> obj.own ?: false }
                .collect(Collectors.toList())
        val newGirls = ownedGirls.stream()
                .filter { g: Girl -> !ownedGirlsIds.contains(g.id) }
                .collect(Collectors.toList())
        for (girl: Girl in newGirls) {
            newTasks.add(HarvestGirlTask(bot, girl.id, (girl.payIn.plus(1).times(1000)).toLong()))
            bot.logger.info("Harvest will start on girl ${girl.id} in ${girl.payIn + 1} seconds")
            ownedGirlsIds.add(girl.id)
        }
        bot.logger.info("Harem size : ${ownedGirlsIds.size}")
        return TaskResult(Delay(3, TimeUnit.HOURS), newTasks)
    }

    private val ownedGirlsIds: MutableList<Int?> = ArrayList()

}