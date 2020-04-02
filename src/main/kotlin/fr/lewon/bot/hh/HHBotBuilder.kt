package fr.lewon.bot.hh

import fr.lewon.bot.hh.operations.FightChampionOperation
import fr.lewon.bot.hh.operations.GetEventInfoOperation
import fr.lewon.bot.hh.operations.GetUserInfoOperation
import fr.lewon.bot.hh.operations.UpgradeStatOperation
import fr.lewon.bot.hh.rest.HHSessionManager
import fr.lewon.bot.hh.tasks.*
import fr.lewon.bot.runner.AbstractBotBuilder
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor
import fr.lewon.bot.runner.bot.props.BotPropertyType
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.session.AbstractSessionManager
import fr.lewon.web.bot.methods.ProcessTrollFightsOperation
import org.springframework.web.reactive.function.client.WebClient

class HHBotBuilder : AbstractBotBuilder(listOf(
        BotPropertyDescriptor("troll_world", BotPropertyType.INTEGER, null, "World containing the troll to farm. NULL to define based on the current world.", isNeeded = false, isNullable = true),
        BotPropertyDescriptor("fight_energy_to_keep", BotPropertyType.INTEGER, 0, "Energy to keep when fighting trolls.", isNeeded = false, isNullable = false),
        BotPropertyDescriptor("tower_energy_to_keep", BotPropertyType.INTEGER, 0, "Energy to keep when fighting in tower of fame.", isNeeded = false, isNullable = false),
        BotPropertyDescriptor("auto_shop_books", BotPropertyType.BOOLEAN, false, "If true, the books in the store will be automatically bought.", isNeeded = false, isNullable = false),
        BotPropertyDescriptor("auto_shop_gifts", BotPropertyType.BOOLEAN, false, "If true, the gifts in the store will be automatically bought.", isNeeded = false, isNullable = false),
        BotPropertyDescriptor("fight_troll_events", BotPropertyType.BOOLEAN, false, "If true, the 'troll_world' property will be ignored during events and girls holding trolls will be fought.", isNeeded = false, isNullable = false)
), listOf(
        FightChampionOperation(),
        GetUserInfoOperation(),
        ProcessTrollFightsOperation(),
        UpgradeStatOperation(),
        GetEventInfoOperation()
)) {

    override fun buildSessionManager(login: String, password: String): AbstractSessionManager {
        return HHSessionManager(login, password, 16000000,
                WebClient.builder()
                        .codecs { c -> c.defaultCodecs().maxInMemorySize(-1) }
                        .baseUrl("https://www.hentaiheroes.com/")
                        .defaultHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .defaultHeader("Accept-Language", "fr-FR,fr;q=0.9,en-GB;q=0.8,en;q=0.7,en-US;q=0.6")
                        .defaultHeader("Cache-Control", "max-age=0")
                        .defaultHeader("Connection", "keep-alive")
                        .defaultHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .defaultHeader("Host", "www.hentaiheroes.com")
                        .defaultHeader("Upgrade-Insecure-Requests", "1")
                        .defaultHeader("Origin", "https://www.hentaiheroes.com")
                        .defaultHeader("Referer", "https://www.hentaiheroes.com/harem/1")
                        .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36"))
    }

    override fun getInitialTasks(bot: Bot): List<BotTask> {
        return listOf(
                GirlsManagerTask(bot),
                AutoShopTask(bot),
                CollectLeagueRewardTask(bot),
                CollectWeeklyRewardTask(bot),
                CollectCompetitionRewardsTask(bot),
                ContinueQuestTask(bot),
                FightArenaOpponentsTask(bot),
                FightTowerOfFameOpponentsTask(bot),
                FightTrollTask(bot),
                ProcessMissionTask(bot)
        )
    }

}