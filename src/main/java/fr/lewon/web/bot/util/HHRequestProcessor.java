package fr.lewon.web.bot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.bot.http.AbstractRequestProcessor;
import fr.lewon.bot.http.RequestHelper;
import fr.lewon.web.bot.entities.OpponentInfoResponse;
import fr.lewon.web.bot.entities.Response;
import fr.lewon.web.bot.entities.SalaryResponse;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.battle.ActionBattleMob;
import fr.lewon.web.bot.entities.input.battle.ActionBattlePlayer;
import fr.lewon.web.bot.entities.input.girl.ActionGirlAllSalaries;
import fr.lewon.web.bot.entities.input.girl.ActionGirlSingleSalary;
import fr.lewon.web.bot.entities.input.leagues.ActionLeaguesGetOpponentInfo;
import fr.lewon.web.bot.entities.input.mission.ActionMissionClaimReward;
import fr.lewon.web.bot.entities.input.mission.ActionMissionGiveGift;
import fr.lewon.web.bot.entities.input.mission.ActionMissionStartMission;
import fr.lewon.web.bot.entities.input.others.PlayerInfos;
import fr.lewon.web.bot.entities.input.others.activity.Mission;
import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.input.quest.ActionQuestNext;

public class HHRequestProcessor extends AbstractRequestProcessor {

	public static final HHRequestProcessor INSTANCE = new HHRequestProcessor();

	private static final String BASE_URL = "https://www.hentaiheroes.com/";

	private static final String PHOENIX_AJAX = "/phoenix-ajax.php";
	private static final String AJAX = "/ajax.php";

	private static final String HOME = "/home.html";
	private static final String HAREM = "/harem.html";
	private static final String BATTLE = "/battle.html";
	private static final String ARENA = "/arena.html";
	private static final String TOWER_OF_FAME = "/tower-of-fame.html";
	private static final String ACTIVITEIS = "/activities.html";
	private static final String MAP = "/map.html";
	private static final String WORLD = "/world";

	private static final String SLASH = "/";

	private HHRequestProcessor() {
		super(new RequestHelper());
	}

	@Override
	protected List<Header> getNeededHeaders() {
		List<Header> neededHeaders = new ArrayList<>();
		neededHeaders.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
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

	public SessionResponse getSession(String login, String password) throws ServerException, IOException {
		String url = BASE_URL + PHOENIX_AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new PlayerInfos(login, password)));
		SessionResponse response = generateResponse(SessionResponse.class, httpResponse).getEntity();
		String hhSess = "";
		String stayOnline = "";
		for (Header h : httpResponse.getHeaders("Set-Cookie")) {
			for (HeaderElement he : h.getElements()) {
				if (he.getName().equals("HH_SESS_13")) {
					hhSess = he.getValue();
				} else if (he.getName().equals("stay_online")) {
					stayOnline = he.getValue();
				}
			}
		}
		String value = "HAPBK=web5; age_verification=1; _pk_ses.2.6e07=1; lang=fr; member_guid=A55C4849-F42D-4A1A-A6C6-11556C261A9C; HH_SESS_13=" + hhSess + "; stay_online=" + stayOnline + "; _pk_id.2.6e07=5ab4aa907c7c5919.1551984183.1.1551995205.1551984183.";
		Header cookie = new BasicHeader("Cookie", value);

		response.setCookies(cookie);
		return response;
	}

	public String getHomeContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + HOME;
		return readAllPageContent(url, session.getCookies());
	}

	public String getHaremContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + HAREM;
		return readAllPageContent(url, session.getCookies());
	}

	public String getMapContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + MAP;
		return readAllPageContent(url, session.getCookies());
	}

	public String getActivitiesContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + ACTIVITEIS;
		return readAllPageContent(url, session.getCookies());
	}

	public String getTowerOfFameContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + TOWER_OF_FAME;
		return readAllPageContent(url, session.getCookies());
	}

	public String getArenaContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + ARENA;
		return readAllPageContent(url, session.getCookies());
	}

	public String getWorldContent(SessionResponse session, String idWorld) throws ServerException, IOException {
		String url = BASE_URL + WORLD + SLASH + idWorld;
		return readAllPageContent(url, session.getCookies());
	}

	public String getBattleArenaContent(SessionResponse session, int idArena) throws ServerException, IOException {
		String url = BASE_URL + BATTLE + "?id_arena=" + idArena;
		return readAllPageContent(url, session.getCookies());
	}

	public String getBattleTrollContent(SessionResponse session, String idTroll) throws ServerException, IOException {
		String url = BASE_URL + BATTLE + "?id_troll=" + idTroll;
		return readAllPageContent(url, session.getCookies());
	}

	public String getLeagueBattleContent(SessionResponse session, String id) throws ServerException, IOException {
		String url = BASE_URL + BATTLE + "?league_battle=1&id_member=" + id;
		return readAllPageContent(url, session.getCookies());
	}

	public OpponentInfoResponse getOpponentInfo(SessionResponse session, String opponentId) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionLeaguesGetOpponentInfo(opponentId)), session.getCookies());
		return generateResponse(OpponentInfoResponse.class, httpResponse).getEntity();
	}

	public SalaryResponse getSalary(SessionResponse session, int which) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionGirlSingleSalary(which)), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse).getEntity();
	}

	public SalaryResponse getAllSalaries(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionGirlAllSalaries()), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse).getEntity();
	}

	public Response startMission(SessionResponse session, Mission mission) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionMissionStartMission(mission.getIdMission(), mission.getIdMemberMission())), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse).getEntity();
	}

	public Response getFinalMissionGift(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionMissionGiveGift()), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse).getEntity();
	}

	public Response claimReward(SessionResponse session, Mission mission) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionMissionClaimReward(mission.getIdMission(), mission.getIdMemberMission())), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse).getEntity();
	}

	public Response fightOpponentPlayer(SessionResponse session, BattlePlayer battlePlayer) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionBattlePlayer(battlePlayer)), session.getCookies());
		return generateResponse(Response.class, httpResponse).getEntity();
	}

	public Response fightOpponentMob(SessionResponse session, BattleMob battleMob) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionBattleMob(battleMob)), session.getCookies());
		return generateResponse(Response.class, httpResponse).getEntity();
	}

	public Response continueQuest(SessionResponse session, Long questId) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = getRequestHelper().processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionQuestNext(questId)), session.getCookies());
		return generateResponse(Response.class, httpResponse).getEntity();
	}

}
