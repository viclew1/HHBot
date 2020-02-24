package fr.lewon.bot.hh.rest

import fr.lewon.bot.hh.entities.input.girl.ActionGirlSingleSalary
import fr.lewon.bot.hh.entities.input.others.PlayerInfo
import fr.lewon.bot.hh.entities.response.SalaryResponse
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

//    @Throws(Exception::class)
//    fun getHomeContent(webClient: WebClient, session: HHSession): String {
//        return webClient.get()
//                .uri(HOME)
//                .cookie(session)
//                .retrieve()
//                .bodyToMono(String.javaClass)
//        return this.readAllPageContent(BASE_URL + HOME, session.getCookieHeader())
//    }

    @Throws(Exception::class)
    fun getHaremContent(webClient: WebClient, session: HHSession): String {
        return webClient.get()
                .uri(HAREM)
                .header(session.cookieHeaderName, session.cookieHeaderValue)
                .retrieve()
                .bodyToMono<String>()
                .block() ?: ""
    }

    //    @Throws(Exception::class)
//    fun getMapContent(session: HHSession?): String {
//        return this.readAllPageContent(BASE_URL + MAP, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getActivitiesContent(session: HHSession?): String {
//        return this.readAllPageContent(BASE_URL + ACTIVITIES, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getChampionsMapContent(session: HHSession?): String {
//        return this.readAllPageContent(BASE_URL + CHAMPIONS_MAP, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getChampionPageContent(session: HHSession?, championId: Int): String {
//        return this.readAllPageContent(BASE_URL + CHAMPIONS + SLASH + championId, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getTowerOfFameContent(session: HHSession?): String {
//        return this.readAllPageContent(BASE_URL + TOWER_OF_FAME, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getArenaContent(session: HHSession?): String {
//        return this.readAllPageContent(BASE_URL + ARENA, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getWorldContent(session: HHSession?, idWorld: String): String {
//        return this.readAllPageContent(BASE_URL + WORLD + SLASH + idWorld, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getBattleArenaContent(session: HHSession?, idArena: Int): String {
//        return this.readAllPageContent("$BASE_URL$BATTLE?id_arena=$idArena", session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getBattleTrollContent(session: HHSession?, idTroll: String): String {
//        return this.readAllPageContent("$BASE_URL$BATTLE?id_troll=$idTroll", session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getLeagueBattleContent(session: HHSession?, id: String?): String {
//        return this.readAllPageContent("$BASE_URL$BATTLE?league_battle=1&id_member=$id", session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getQuestContent(session: HHSession?, questId: Long?): String {
//        return this.readAllPageContent(BASE_URL + QUEST + SLASH + questId, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getShopContent(session: HHSession?): String {
//        return this.readAllPageContent(BASE_URL + SHOP, session.getCookieHeader())
//    }
//
//    @Throws(Exception::class)
//    fun getOpponentInfo(session: HHSession, opponentId: String?): OpponentInfoResponse {
//        return this.processPostRequest(OpponentInfoResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionLeaguesGetOpponentInfo(opponentId)), session.cookieHeader).entity
//    }
//
    @Throws(Exception::class)
    fun getSalary(webClient: WebClient, session: HHSession, which: Int): SalaryResponse? {
        return webClient.post()
                .uri(AJAX)
                .header(session.cookieHeaderName, session.cookieHeaderValue)
                .bodyValue(ActionGirlSingleSalary(which))
                .retrieve()
                .bodyToMono<SalaryResponse>()
                .block()
    }
//
//    @Throws(Exception::class)
//    fun getAllSalaries(session: HHSession): SalaryResponse {
//        return this.processPostRequest(SalaryResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionGirlAllSalaries()), session.cookieHeader).entity
//    }
//
//    @Throws(Exception::class)
//    fun draftChampionFight(session: HHSession, idChampion: Int?, girlsToKeep: List<Int?>?): DraftResponse {
//        return this.processPostRequest(DraftResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionChampionsTeamDraft(idChampion, girlsToKeep)), session.cookieHeader).entity
//    }
//
//    @Throws(Exception::class)
//    fun fightChampion(session: HHSession?, currency: Currency, championId: Int?, girls: List<Int?>?): TeamBattleResponse {
//        return this.processPostRequest(TeamBattleResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionTeamBattleChampion(currency, championId, girls)), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun startMission(session: HHSession?, mission: Mission?): Response {
//        return this.processPostRequest(SalaryResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionMissionStartMission(mission.getIdMission(), mission.getIdMemberMission())), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun getFinalMissionGift(session: HHSession?): Response {
//        return this.processPostRequest(SalaryResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionMissionGiveGift()), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun claimReward(session: HHSession?, mission: Mission?): Response {
//        return this.processPostRequest(SalaryResponse::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionMissionClaimReward(mission.getIdMission(), mission.getIdMemberMission())), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun fightOpponentPlayer(session: HHSession?, battlePlayer: BattlePlayer): Response {
//        return this.processPostRequest(Response::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionBattlePlayer(battlePlayer)), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun fightOpponentMob(session: HHSession?, battleMob: BattleMob?): Response {
//        return this.processPostRequest(Response::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionBattleMob(battleMob)), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun continueQuest(session: HHSession?, questId: Long?): Response {
//        return this.processPostRequest(Response::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionQuestNext(questId)), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun upgradeStat(session: HHSession?, statToUpgrade: Int): Response {
//        return this.processPostRequest(Response::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionUpgradeStat(statToUpgrade)), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun buyItem(item: Item?, session: HHSession?): Response {
//        return this.processPostRequest(Response::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionBuyItem(item)), session.getCookieHeader()).entity
//    }
//
//    @Throws(Exception::class)
//    fun collectCompetitionRewards(idContest: Int?, session: HHSession?): Response {
//        return this.processPostRequest(Response::class.java, BASE_URL + AJAX,
//                bodyBuilder.generateBody(ActionContestGiveReward(idContest)), session.getCookieHeader()).entity
//    }

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