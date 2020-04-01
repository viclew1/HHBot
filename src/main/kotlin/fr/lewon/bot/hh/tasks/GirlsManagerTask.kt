package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.girls.Girl
import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import java.util.concurrent.TimeUnit

class GirlsManagerTask(bot: Bot) : BotTask("Girls manager", bot) {

    override fun doExecute(): TaskResult {
        val newHarvestTasks: MutableList<BotTask> = ArrayList()
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        val requestProcessor = HHRequestProcessor()
        val haremContent = requestProcessor.getHaremContent(webClient, session)

        val newGirls = HtmlAnalyzer.INSTANCE.findAllGirls(haremContent)
                .filter { it.own ?: false }
                .filter { !ownedGirlsIds.contains(it.id) }

        for (girl: Girl in newGirls) {
            newHarvestTasks.add(HarvestGirlTask(bot, girl.id, (girl.payIn.plus(1).times(1000)).toLong()))
            logger.info("Harvest will start on girl ${girl.id} in ${girl.payIn + 1} seconds")
            ownedGirlsIds.add(girl.id)
        }
        logger.info("Harem size : ${ownedGirlsIds.size}")
        return TaskResult(Delay(3, TimeUnit.HOURS), newHarvestTasks)
    }

    private val ownedGirlsIds: MutableList<Int?> = ArrayList()

}