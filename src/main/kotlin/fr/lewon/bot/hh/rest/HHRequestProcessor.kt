package fr.lewon.bot.hh.rest

import fr.lewon.bot.hh.entities.activities.Mission
import fr.lewon.bot.hh.entities.battle.BattleMob
import fr.lewon.bot.hh.entities.battle.BattlePlayer
import fr.lewon.bot.hh.entities.battle.BattlePlayerSeason
import fr.lewon.bot.hh.entities.enums.Currency
import fr.lewon.bot.hh.entities.input.battle.ActionBattleMob
import fr.lewon.bot.hh.entities.input.battle.ActionBattlePlayer
import fr.lewon.bot.hh.entities.input.battle.ActionBattlePlayerSeason
import fr.lewon.bot.hh.entities.input.champions.ActionChampionsTeamDraft
import fr.lewon.bot.hh.entities.input.contest.ActionContestGiveReward
import fr.lewon.bot.hh.entities.input.girl.ActionGirlAllSalaries
import fr.lewon.bot.hh.entities.input.girl.ActionGirlSingleSalary
import fr.lewon.bot.hh.entities.input.leagues.ActionLeagueReward
import fr.lewon.bot.hh.entities.input.leagues.ActionLeaguesGetOpponentInfo
import fr.lewon.bot.hh.entities.input.mission.ActionMissionClaimReward
import fr.lewon.bot.hh.entities.input.mission.ActionMissionGiveGift
import fr.lewon.bot.hh.entities.input.mission.ActionMissionStartMission
import fr.lewon.bot.hh.entities.input.others.PlayerInfo
import fr.lewon.bot.hh.entities.input.powerplaces.ActionPlaceOfPowerCollect
import fr.lewon.bot.hh.entities.input.powerplaces.ActionPlaceOfPowerStart
import fr.lewon.bot.hh.entities.input.quest.ActionQuestNext
import fr.lewon.bot.hh.entities.input.rewards.ActionWeeklyReward
import fr.lewon.bot.hh.entities.input.shop.ActionBuyItem
import fr.lewon.bot.hh.entities.input.stat.ActionUpgradeStat
import fr.lewon.bot.hh.entities.input.teambattle.ActionTeamBattleChampion
import fr.lewon.bot.hh.entities.response.*
import fr.lewon.bot.hh.entities.shop.Item
import fr.lewon.bot.runner.session.helpers.FUEBodyBuilder
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient

class HHRequestProcessor {

    private val cookieStore = LinkedMultiValueMap<String, String>()

    @Throws(Exception::class)
    fun login(webClient: WebClient, login: String, password: String) {
        cookieStore.clear()
        postForBody<String>(webClient, PHOENIX_AJAX, PlayerInfo(login, password))
        if (!cookieStore.contains("stay_online") || !cookieStore.contains("HH_SESS_13")) {
            throw Exception("Unable to connect to Hentai Heroes, website may be down")
        }
        cookieStore["lang"] = "fr"
        cookieStore["_pk_id.2.6e07"] = "cda6c741be7d8090.1562523528.2.1562529541.1562528524."
        cookieStore["_pk_ses.2.6e07"] = "1"
        cookieStore["age_verification"] = "1"
    }

    @Throws(Exception::class)
    fun getHomeContent(webClient: WebClient): String {
        return readAllPage(webClient, HOME)
    }

    @Throws(Exception::class)
    fun getHaremContent(webClient: WebClient): String {
        return readAllPage(webClient, HAREM)
    }

    @Throws(Exception::class)
    fun getMapContent(webClient: WebClient): String {
        return readAllPage(webClient, MAP)
    }

    @Throws(Exception::class)
    fun getActivitiesContent(webClient: WebClient): String {
        return readAllPage(webClient, ACTIVITIES)
    }

    @Throws(Exception::class)
    fun getChampionsMapContent(webClient: WebClient): String {
        return readAllPage(webClient, CHAMPIONS_MAP)
    }

    @Throws(Exception::class)
    fun getChampionPageContent(webClient: WebClient, championId: Int): String {
        return readAllPage(webClient, CHAMPIONS + SLASH + championId)
    }

    @Throws(Exception::class)
    fun getTowerOfFameContent(webClient: WebClient): String {
        return readAllPage(webClient, TOWER_OF_FAME)
    }

    @Throws(Exception::class)
    fun getArenaContent(webClient: WebClient): String {
        return readAllPage(webClient, ARENA)
    }

    @Throws(Exception::class)
    fun getWorldContent(webClient: WebClient, idWorld: String): String {
        return readAllPage(webClient, WORLD + SLASH + idWorld)
    }

    @Throws(Exception::class)
    fun getBattleArenaContent(webClient: WebClient, idArena: Int): String {
        return readAllPage(webClient, "$BATTLE?id_arena=$idArena")
    }

    @Throws(Exception::class)
    fun getBattleTrollContent(webClient: WebClient, idTroll: String): String {
        return readAllPage(webClient, "$BATTLE?id_troll=$idTroll")
    }

    @Throws(Exception::class)
    fun getLeagueBattleContent(webClient: WebClient, id: String): String {
        return readAllPage(webClient, "$BATTLE?league_battle=1&id_member=$id")
    }

    @Throws(Exception::class)
    fun getBattleSeasonArenaContent(webClient: WebClient, idSeasonArena: String): String {
        return readAllPage(webClient, "$BATTLE?id_season_arena=$idSeasonArena")
    }

    @Throws(Exception::class)
    fun getQuestContent(webClient: WebClient, questId: Long): String {
        return readAllPage(webClient, QUEST + SLASH + questId)
    }

    @Throws(Exception::class)
    fun getShopContent(webClient: WebClient): String {
        return readAllPage(webClient, SHOP)
    }

    @Throws(Exception::class)
    fun getSeasonContent(webClient: WebClient): String {
        return readAllPage(webClient, SEASON)
    }

    @Throws(Exception::class)
    fun getSeasonArenaContent(webClient: WebClient): String {
        return readAllPage(webClient, SEASON_ARENA)
    }

    @Throws(Exception::class)
    fun getOpponentInfo(webClient: WebClient, opponentId: String): OpponentInfoResponse? {
        return postForBody(webClient, AJAX, ActionLeaguesGetOpponentInfo(opponentId))
    }

    @Throws(Exception::class)
    fun getSalary(webClient: WebClient, which: Int): SalaryResponse? {
        return postForBody(webClient, AJAX, ActionGirlSingleSalary(which))
    }

    @Throws(Exception::class)
    fun getAllSalaries(webClient: WebClient): SalaryResponse? {
        return postForBody(webClient, AJAX, ActionGirlAllSalaries())
    }

    @Throws(Exception::class)
    fun draftChampionFight(webClient: WebClient, idChampion: Int, girlsToKeep: List<Int>): DraftResponse? {
        return postForBody(webClient, AJAX, ActionChampionsTeamDraft(idChampion, girlsToKeep))
    }

    @Throws(Exception::class)
    fun fightChampion(webClient: WebClient, currency: Currency, championId: Int, girls: List<Int>): TeamBattleResponse? {
        return postForBody(webClient, AJAX, ActionTeamBattleChampion(currency, championId, girls))
    }

    @Throws(Exception::class)
    fun startMission(webClient: WebClient, mission: Mission): Response? {
        return postForBody(webClient, AJAX, ActionMissionStartMission(mission))
    }

    @Throws(Exception::class)
    fun startPlaceOfPower(webClient: WebClient, placeOfPowerId: Int, selectedGirls: List<Int>): Response? {
        return postForBody(webClient, AJAX, ActionPlaceOfPowerStart(placeOfPowerId, selectedGirls))
    }

    @Throws(Exception::class)
    fun collectPlaceOfPower(webClient: WebClient, placeOfPowerId: Int): Response? {
        return postForBody(webClient, AJAX, ActionPlaceOfPowerCollect(placeOfPowerId))
    }

    @Throws(Exception::class)
    fun getFinalMissionGift(webClient: WebClient): Response? {
        return postForBody(webClient, AJAX, ActionMissionGiveGift())
    }

    @Throws(Exception::class)
    fun claimReward(webClient: WebClient, mission: Mission): Response? {
        return postForBody(webClient, AJAX, ActionMissionClaimReward(mission))
    }

    @Throws(Exception::class)
    fun fightOpponentPlayer(webClient: WebClient, battlePlayer: BattlePlayer): Response? {
        return postForBody(webClient, AJAX, ActionBattlePlayer(battlePlayer))
    }

    @Throws(Exception::class)
    fun fightOpponentMob(webClient: WebClient, battleMob: BattleMob): Response? {
        return postForBody(webClient, AJAX, ActionBattleMob(battleMob))
    }

    @Throws(Exception::class)
    fun fightOpponentSeason(webClient: WebClient, battlePlayerSeason: BattlePlayerSeason): Response? {
        return postForBody(webClient, AJAX, ActionBattlePlayerSeason(battlePlayerSeason))
    }

    @Throws(Exception::class)
    fun continueQuest(webClient: WebClient, questId: Long): Response? {
        return postForBody(webClient, AJAX, ActionQuestNext(questId))
    }

    @Throws(Exception::class)
    fun upgradeStat(webClient: WebClient, statToUpgrade: Int): Response? {
        return postForBody(webClient, AJAX, ActionUpgradeStat(statToUpgrade))
    }

    @Throws(Exception::class)
    fun buyItem(webClient: WebClient, item: Item): Response? {
        return postForBody(webClient, AJAX, ActionBuyItem(item))
    }

    @Throws(Exception::class)
    fun collectCompetitionRewards(webClient: WebClient, idContest: Int): Response? {
        return postForBody(webClient, AJAX, ActionContestGiveReward(idContest))
    }

    @Throws(Exception::class)
    fun claimWeeklyRewards(webClient: WebClient): Response? {
        return postForBody(webClient, AJAX, ActionWeeklyReward())
    }

    @Throws(Exception::class)
    fun claimLeagueRewards(webClient: WebClient): Response? {
        return postForBody(webClient, AJAX, ActionLeagueReward())
    }

    @Synchronized
    private fun readAllPage(webClient: WebClient, uri: String): String {
        val response = webClient.get()
                .uri(uri)
                .header("Cookie", cookieStore.entries.joinToString("; ") { (k, v) -> "$k=${v[0]}" })
                .exchange()
                .block()
        response?.cookies()?.forEach { (n, u) -> cookieStore[n] = u[0].value }
        return response?.bodyToMono(String::class.java)?.block() ?: ""
    }

    @Synchronized
    private fun <T> postForBody(webClient: WebClient, uri: String, body: Any, responseType: Class<T>): T? {
        val response = webClient.post()
                .uri(uri)
                .header("Cookie", cookieStore.entries.joinToString("; ") { (k, v) -> "$k=${v[0]}" })
                .bodyValue(FUEBodyBuilder().generateBody(body))
                .exchange().block()
        response?.cookies()?.forEach { (n, u) -> cookieStore[n] = u[0].value }
        return response?.bodyToMono(responseType)?.block()
    }

    private inline fun <reified T> postForBody(webClient: WebClient, uri: String, body: Any): T? {
        return postForBody(webClient, uri, body, T::class.java)
    }

    companion object {
        private const val PHOENIX_AJAX = "/phoenix-ajax.php"
        private const val AJAX = "/ajax.php"
        private const val HOME = "/home.html"
        private const val SHOP = "/shop.html"
        private const val HAREM = "/harem.html"
        private const val BATTLE = "/battle.html"
        private const val ARENA = "/arena.html"
        private const val TOWER_OF_FAME = "/tower-of-fame.html"
        private const val ACTIVITIES = "/activities.html"
        private const val CHAMPIONS_MAP = "champions-map.html"
        private const val MAP = "/map.html"
        private const val SEASON = "/season.html"
        private const val SEASON_ARENA = "/season-arena.html"
        private const val WORLD = "/world"
        private const val CHAMPIONS = "/champions"
        private const val QUEST = "/quest"
        private const val SLASH = "/"
    }
}