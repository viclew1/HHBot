package fr.lewon.bot.hh.rest

import fr.lewon.bot.hh.entities.activities.Mission
import fr.lewon.bot.hh.entities.battle.BattleMob
import fr.lewon.bot.hh.entities.battle.BattlePlayer
import fr.lewon.bot.hh.entities.enums.Currency
import fr.lewon.bot.hh.entities.input.battle.ActionBattleMob
import fr.lewon.bot.hh.entities.input.battle.ActionBattlePlayer
import fr.lewon.bot.hh.entities.input.champions.ActionChampionsTeamDraft
import fr.lewon.bot.hh.entities.input.contest.ActionContestGiveReward
import fr.lewon.bot.hh.entities.input.girl.ActionGirlAllSalaries
import fr.lewon.bot.hh.entities.input.girl.ActionGirlSingleSalary
import fr.lewon.bot.hh.entities.input.leagues.ActionLeaguesGetOpponentInfo
import fr.lewon.bot.hh.entities.input.mission.ActionMissionClaimReward
import fr.lewon.bot.hh.entities.input.mission.ActionMissionGiveGift
import fr.lewon.bot.hh.entities.input.mission.ActionMissionStartMission
import fr.lewon.bot.hh.entities.input.others.PlayerInfo
import fr.lewon.bot.hh.entities.input.quest.ActionQuestNext
import fr.lewon.bot.hh.entities.input.shop.ActionBuyItem
import fr.lewon.bot.hh.entities.input.stat.ActionUpgradeStat
import fr.lewon.bot.hh.entities.input.teambattle.ActionTeamBattleChampion
import fr.lewon.bot.hh.entities.response.*
import fr.lewon.bot.hh.entities.shop.Item
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

class HHRequestProcessor {

    @Throws(Exception::class)
    fun getSession(webClient: WebClient, login: String, password: String): Mono<ClientResponse> {
        return webClient.post()
                .uri(PHOENIX_AJAX)
                .bodyValue(PlayerInfo(login, password))
                .exchange()
    }

    @Throws(Exception::class)
    fun getHomeContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, HOME)
    }

    @Throws(Exception::class)
    fun getHaremContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, HAREM)
    }

    @Throws(Exception::class)
    fun getMapContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, MAP)
    }

    @Throws(Exception::class)
    fun getActivitiesContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, ACTIVITIES)
    }

    @Throws(Exception::class)
    fun getChampionsMapContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, CHAMPIONS_MAP)
    }

    @Throws(Exception::class)
    fun getChampionPageContent(webClient: WebClient, session: HHSession, championId: Int): String {
        return readAllPage(webClient, session, CHAMPIONS + SLASH + championId)
    }

    @Throws(Exception::class)
    fun getTowerOfFameContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, TOWER_OF_FAME)
    }

    @Throws(Exception::class)
    fun getArenaContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, ARENA)
    }

    @Throws(Exception::class)
    fun getWorldContent(webClient: WebClient, session: HHSession, idWorld: String): String {
        return readAllPage(webClient, session, WORLD + SLASH + idWorld)
    }

    @Throws(Exception::class)
    fun getBattleArenaContent(webClient: WebClient, session: HHSession, idArena: Int): String {
        return readAllPage(webClient, session, "$BATTLE?id_arena=$idArena")
    }

    @Throws(Exception::class)
    fun getBattleTrollContent(webClient: WebClient, session: HHSession, idTroll: Int): String {
        return readAllPage(webClient, session, "$BATTLE?id_troll=$idTroll")
    }

    @Throws(Exception::class)
    fun getLeagueBattleContent(webClient: WebClient, session: HHSession, id: String): String {
        return readAllPage(webClient, session, "$BATTLE?league_battle=1&id_member=$id")
    }

    @Throws(Exception::class)
    fun getQuestContent(webClient: WebClient, session: HHSession, questId: Long): String {
        return readAllPage(webClient, session, QUEST + SLASH + questId)
    }

    @Throws(Exception::class)
    fun getShopContent(webClient: WebClient, session: HHSession): String {
        return readAllPage(webClient, session, HOME)
    }

    @Throws(Exception::class)
    fun getOpponentInfo(webClient: WebClient, session: HHSession, opponentId: String): OpponentInfoResponse? {
        return postForBody(webClient, session, AJAX, ActionLeaguesGetOpponentInfo(opponentId))
    }

    @Throws(Exception::class)
    fun getSalary(webClient: WebClient, session: HHSession, which: Int): SalaryResponse? {
        return postForBody(webClient, session, AJAX, ActionGirlSingleSalary(which))
    }

    @Throws(Exception::class)
    fun getAllSalaries(webClient: WebClient, session: HHSession): SalaryResponse? {
        return postForBody(webClient, session, AJAX, ActionGirlAllSalaries())
    }

    @Throws(Exception::class)
    fun draftChampionFight(webClient: WebClient, session: HHSession, idChampion: Int, girlsToKeep: List<Int>): DraftResponse? {
        return postForBody(webClient, session, AJAX, ActionChampionsTeamDraft(idChampion, girlsToKeep))
    }

    @Throws(Exception::class)
    fun fightChampion(webClient: WebClient, session: HHSession, currency: Currency, championId: Int, girls: List<Int>): TeamBattleResponse? {
        return postForBody(webClient, session, AJAX, ActionTeamBattleChampion(currency, championId, girls))
    }

    @Throws(Exception::class)
    fun startMission(webClient: WebClient, session: HHSession, mission: Mission): Response? {
        return postForBody(webClient, session, AJAX, ActionMissionStartMission(mission))
    }

    @Throws(Exception::class)
    fun getFinalMissionGift(webClient: WebClient, session: HHSession): Response? {
        return postForBody(webClient, session, AJAX, ActionMissionGiveGift())
    }

    @Throws(Exception::class)
    fun claimReward(webClient: WebClient, session: HHSession, mission: Mission): Response? {
        return postForBody(webClient, session, AJAX, ActionMissionClaimReward(mission))
    }

    @Throws(Exception::class)
    fun fightOpponentPlayer(webClient: WebClient, session: HHSession, battlePlayer: BattlePlayer): Response? {
        return postForBody(webClient, session, AJAX, ActionBattlePlayer(battlePlayer))
    }

    @Throws(Exception::class)
    fun fightOpponentMob(webClient: WebClient, session: HHSession, battleMob: BattleMob): Response? {
        return postForBody(webClient, session, AJAX, ActionBattleMob(battleMob))
    }

    @Throws(Exception::class)
    fun continueQuest(webClient: WebClient, session: HHSession, questId: Long): Response? {
        return postForBody(webClient, session, AJAX, ActionQuestNext(questId))
    }

    @Throws(Exception::class)
    fun upgradeStat(webClient: WebClient, session: HHSession, statToUpgrade: Int): Response? {
        return postForBody(webClient, session, AJAX, ActionUpgradeStat(statToUpgrade))
    }

    @Throws(Exception::class)
    fun buyItem(webClient: WebClient, session: HHSession, item: Item): Response? {
        return postForBody(webClient, session, AJAX, ActionBuyItem(item))
    }

    @Throws(Exception::class)
    fun collectCompetitionRewards(webClient: WebClient, session: HHSession, idContest: Int): Response? {
        return postForBody(webClient, session, AJAX, ActionContestGiveReward(idContest))
    }

    @Throws(Exception::class)
    private fun readAllPage(webClient: WebClient, session: HHSession, uri: String): String {
        return webClient.get()
                .uri(uri)
                .header(session.cookieHeaderName, session.cookieHeaderValue)
                .retrieve()
                .bodyToMono<String>()
                .block() ?: ""
    }

    private inline fun <reified T> postForBody(webClient: WebClient, session: HHSession, uri: String, body: Any): T? {
        return webClient.post()
                .uri(uri)
                .header(session.cookieHeaderName, session.cookieHeaderValue)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(T::class.java)
                .block()
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
        private const val WORLD = "/world"
        private const val CHAMPIONS = "/champions"
        private const val QUEST = "/quest"
        private const val SLASH = "/"
    }
}