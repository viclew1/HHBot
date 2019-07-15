package fr.lewon.web.bot.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;

import fr.lewon.bot.http.AbstractRequestProcessor;
import fr.lewon.bot.http.body.HttpBodyBuilder;
import fr.lewon.bot.http.body.urlencoded.FUEBuilder;
import fr.lewon.web.bot.entities.activities.Mission;
import fr.lewon.web.bot.entities.battle.BattleMob;
import fr.lewon.web.bot.entities.battle.BattlePlayer;
import fr.lewon.web.bot.entities.enums.Currency;
import fr.lewon.web.bot.entities.input.battle.ActionBattleMob;
import fr.lewon.web.bot.entities.input.battle.ActionBattlePlayer;
import fr.lewon.web.bot.entities.input.champions.ActionChampionsTeamDraft;
import fr.lewon.web.bot.entities.input.contest.ActionContestGiveReward;
import fr.lewon.web.bot.entities.input.girl.ActionGirlAllSalaries;
import fr.lewon.web.bot.entities.input.girl.ActionGirlSingleSalary;
import fr.lewon.web.bot.entities.input.leagues.ActionLeaguesGetOpponentInfo;
import fr.lewon.web.bot.entities.input.mission.ActionMissionClaimReward;
import fr.lewon.web.bot.entities.input.mission.ActionMissionGiveGift;
import fr.lewon.web.bot.entities.input.mission.ActionMissionStartMission;
import fr.lewon.web.bot.entities.input.others.PlayerInfos;
import fr.lewon.web.bot.entities.input.quest.ActionQuestNext;
import fr.lewon.web.bot.entities.input.shop.ActionBuyItem;
import fr.lewon.web.bot.entities.input.stat.ActionUpgradeStat;
import fr.lewon.web.bot.entities.input.teambattle.ActionTeamBattleChampion;
import fr.lewon.web.bot.entities.response.DraftResponse;
import fr.lewon.web.bot.entities.response.OpponentInfoResponse;
import fr.lewon.web.bot.entities.response.Response;
import fr.lewon.web.bot.entities.response.SalaryResponse;
import fr.lewon.web.bot.entities.response.TeamBattleResponse;
import fr.lewon.web.bot.entities.shop.Item;

public class HHRequestProcessor extends AbstractRequestProcessor {

	private static final String BASE_URL = "https://www.hentaiheroes.com/";

	private static final String PHOENIX_AJAX = "/phoenix-ajax.php";
	private static final String AJAX = "/ajax.php";

	private static final String HOME = "/home.html";
	private static final String SHOP = "/shop.html";
	private static final String HAREM = "/harem.html";
	private static final String BATTLE = "/battle.html";
	private static final String ARENA = "/arena.html";
	private static final String TOWER_OF_FAME = "/tower-of-fame.html";
	private static final String ACTIVITIES = "/activities.html";
	private static final String CHAMPIONS_MAP = "champions-map.html";
	private static final String MAP = "/map.html";
	private static final String WORLD = "/world";
	private static final String CHAMPIONS = "/champions";
	private static final String QUEST = "/quest";

	private static final String SLASH = "/";

	
	private HttpBodyBuilder bodyBuilder = new FUEBuilder();
	
	@Override
	protected List<Header> getNeededHeaders() {
		List<Header> neededHeaders = new ArrayList<>();
		neededHeaders.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3"));
		neededHeaders.add(new BasicHeader("Accept-Language", "fr-FR,fr;q=0.9,en-GB;q=0.8,en;q=0.7,en-US;q=0.6"));
		neededHeaders.add(new BasicHeader("Cache-Control", "max-age=0"));
		neededHeaders.add(new BasicHeader("Connection", "keep-alive"));
		neededHeaders.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
		neededHeaders.add(new BasicHeader("Host", "www.hentaiheroes.com"));
		neededHeaders.add(new BasicHeader("Upgrade-Insecure-Requests", "1"));
		neededHeaders.add(new BasicHeader("Origin", "https://www.hentaiheroes.com"));
		neededHeaders.add(new BasicHeader("Referer", "https://www.hentaiheroes.com/harem/1"));
		neededHeaders.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36"));
		return neededHeaders;
	}

	public HttpResponse getSession(String login, String password) throws Exception {
		return processPostRequest(BASE_URL + PHOENIX_AJAX, 
				bodyBuilder.generateBody(new PlayerInfos(login, password)));
	}

	public String getHomeContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + HOME, session.getCookieHeader());
	}

	public String getHaremContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + HAREM, session.getCookieHeader());
	}

	public String getMapContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + MAP, session.getCookieHeader());
	}

	public String getActivitiesContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + ACTIVITIES, session.getCookieHeader());
	}
	
	public String getChampionsMapContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + CHAMPIONS_MAP, session.getCookieHeader());
	}
	
	public String getChampionPageContent(HHSession session, Integer championId) throws Exception {
		return readAllPageContent(BASE_URL + CHAMPIONS + SLASH + championId, session.getCookieHeader());
	}

	public String getTowerOfFameContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + TOWER_OF_FAME, session.getCookieHeader());
	}

	public String getArenaContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + ARENA, session.getCookieHeader());
	}

	public String getWorldContent(HHSession session, String idWorld) throws Exception {
		return readAllPageContent(BASE_URL + WORLD + SLASH + idWorld, session.getCookieHeader());
	}

	public String getBattleArenaContent(HHSession session, int idArena) throws Exception {
		return readAllPageContent(BASE_URL + BATTLE + "?id_arena=" + idArena, session.getCookieHeader());
	}

	public String getBattleTrollContent(HHSession session, String idTroll) throws Exception {
		return readAllPageContent(BASE_URL + BATTLE + "?id_troll=" + idTroll, session.getCookieHeader());
	}

	public String getLeagueBattleContent(HHSession session, String id) throws Exception {
		return readAllPageContent(BASE_URL + BATTLE + "?league_battle=1&id_member=" + id, session.getCookieHeader());
	}

	public String getQuestContent(HHSession session, Long questId) throws Exception {
		return readAllPageContent(BASE_URL + QUEST + SLASH + questId, session.getCookieHeader());
	}
	
	public String getShopContent(HHSession session) throws Exception {
		return readAllPageContent(BASE_URL + SHOP, session.getCookieHeader());
	}

	public OpponentInfoResponse getOpponentInfo(HHSession session, String opponentId) throws Exception {
		return processPostRequest(OpponentInfoResponse.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionLeaguesGetOpponentInfo(opponentId)), session.getCookieHeader()).getEntity();
	}

	public SalaryResponse getSalary(HHSession session, int which) throws Exception {
		return processPostRequest(SalaryResponse.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionGirlSingleSalary(which)), session.getCookieHeader()).getEntity();
	}

	public SalaryResponse getAllSalaries(HHSession session) throws Exception {
		return processPostRequest(SalaryResponse.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionGirlAllSalaries()), session.getCookieHeader()).getEntity();
	}
	
	public DraftResponse draftChampionFight(HHSession session, Integer idChampion, List<Integer> girlsToKeep) throws Exception {
		return processPostRequest(DraftResponse.class, BASE_URL + AJAX,
				bodyBuilder.generateBody(new ActionChampionsTeamDraft(idChampion, girlsToKeep)), session.getCookieHeader()).getEntity();
	}
	
	public TeamBattleResponse fightChampion(HHSession session, Currency currency, Integer championId, List<Integer> girls) throws Exception {
		return processPostRequest(TeamBattleResponse.class, BASE_URL + AJAX,
				bodyBuilder.generateBody(new ActionTeamBattleChampion(currency, championId, girls)), session.getCookieHeader()).getEntity();
	}

	public Response startMission(HHSession session, Mission mission) throws Exception {
		return processPostRequest(SalaryResponse.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionMissionStartMission(mission.getIdMission(), mission.getIdMemberMission())), session.getCookieHeader()).getEntity();
	}

	public Response getFinalMissionGift(HHSession session) throws Exception {
		return processPostRequest(SalaryResponse.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionMissionGiveGift()), session.getCookieHeader()).getEntity();
	}

	public Response claimReward(HHSession session, Mission mission) throws Exception {
		return processPostRequest(SalaryResponse.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionMissionClaimReward(mission.getIdMission(), mission.getIdMemberMission())), session.getCookieHeader()).getEntity();
	}

	public Response fightOpponentPlayer(HHSession session, BattlePlayer battlePlayer) throws Exception {
		return processPostRequest(Response.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionBattlePlayer(battlePlayer)), session.getCookieHeader()).getEntity();
	}

	public Response fightOpponentMob(HHSession session, BattleMob battleMob) throws Exception {
		return processPostRequest(Response.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionBattleMob(battleMob)), session.getCookieHeader()).getEntity();
	}

	public Response continueQuest(HHSession session, Long questId) throws Exception {
		return processPostRequest(Response.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionQuestNext(questId)), session.getCookieHeader()).getEntity();
	}

	public Response upgradeStat(HHSession session, int statToUpgrade) throws Exception {
		return processPostRequest(Response.class, BASE_URL + AJAX, 
				bodyBuilder.generateBody(new ActionUpgradeStat(statToUpgrade)), session.getCookieHeader()).getEntity();
	}

	public Response buyItem(Item item, HHSession session) throws Exception {
		return processPostRequest(Response.class, BASE_URL + AJAX,
				bodyBuilder.generateBody(new ActionBuyItem(item)), session.getCookieHeader()).getEntity();
	}

	public Response collectCompetitionRewards(Integer idContest, HHSession session) throws Exception {
		return processPostRequest(Response.class, BASE_URL + AJAX,
				bodyBuilder.generateBody(new ActionContestGiveReward(idContest)), session.getCookieHeader()).getEntity();
	}


}
