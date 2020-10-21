package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class StartChampionFightsTask(bot: Bot): BotTask("Start champion fights", bot) {

    override fun doExecute(): TaskResult {
        val fightChampionTasks = ArrayList<BotTask>()
        for (entry in bot.sharedProperties.entries) {
            val key = entry.key
            val value = entry.value
            if (key.startsWith("CHAMPION_", true) && value == true) {
                val championId = key.substringAfter('_').toInt()
                fightChampionTasks.add(ChampionFightTask(bot, championId))
                bot.logger.info("Restarting champion fight : Champion [$championId]")
            }
        }
        return TaskResult(tasksToCreate = fightChampionTasks)
    }

}