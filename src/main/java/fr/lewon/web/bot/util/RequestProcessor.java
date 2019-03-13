package fr.lewon.web.bot.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

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
import fr.lewon.web.bot.entities.output.OpponentInfoResponse;
import fr.lewon.web.bot.entities.output.Response;
import fr.lewon.web.bot.entities.output.SalaryResponse;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.exceptions.ServerException;
import fr.lewon.web.bot.properties.GameProperties;

public enum RequestProcessor {

	INSTANCE;

	private static final String BASE_URL = GameProperties.INSTANCE.getUrl();

	private static final String PHOENIX_AJAX = "/phoenix-ajax.php";
	private static final String AJAX = "/ajax.php";

	private static final String HAREM = "/harem.html";
	private static final String BATTLE = "/battle.html";
	private static final String ARENA = "/arena.html";
	private static final String TOWER_OF_FAME = "/tower-of-fame.html";
	private static final String ACTIVITEIS = "/activities.html";
	private static final String MAP = "/map.html";
	private static final String WORLD = "/world";

	private static final String SLASH = "/";

	private RequestProcessor() {}

	public SessionResponse getSession(String login, String password) throws ServerException, IOException {
		String url = BASE_URL + PHOENIX_AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new PlayerInfos(login, password)));
		SessionResponse response = generateResponse(SessionResponse.class, httpResponse);
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

	private String readAllContent(SessionResponse session, String url) throws IOException, ServerException {
		HttpResponse httpResponse = RequestHelper.INSTANCE.processGetRequest(url, session.getCookies());
		return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
	}

	public String getHaremContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + HAREM;
		return readAllContent(session, url);
	}

	public String getMapContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + MAP;
		return readAllContent(session, url);
	}

	public String getActivitiesContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + ACTIVITEIS;
		return readAllContent(session, url);
	}

	public String getTowerOfFameContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + TOWER_OF_FAME;
		return readAllContent(session, url);
	}

	public String getArenaContent(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + ARENA;
		return readAllContent(session, url);
	}

	public String getWorldContent(SessionResponse session, String idWorld) throws ServerException, IOException {
		String url = BASE_URL + WORLD + SLASH + idWorld;
		return readAllContent(session, url);
	}

	public String getBattleArenaContent(SessionResponse session, int idArena) throws ServerException, IOException {
		String url = BASE_URL + BATTLE + "?id_arena=" + idArena;
		return readAllContent(session, url);
	}

	public String getBattleTrollContent(SessionResponse session, String idTroll) throws ServerException, IOException {
		String url = BASE_URL + BATTLE + "?id_troll=" + idTroll;
		return readAllContent(session, url);
	}

	public String getLeagueBattleContent(SessionResponse session, String id) throws ServerException, IOException {
		String url = BASE_URL + BATTLE + "?league_battle=1&id_member=" + id;
		return readAllContent(session, url);
	}

	public OpponentInfoResponse getOpponentInfo(SessionResponse session, String opponentId) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionLeaguesGetOpponentInfo(opponentId)), session.getCookies());
		return generateResponse(OpponentInfoResponse.class, httpResponse);
	}

	public SalaryResponse getSalary(SessionResponse session, int which) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionGirlSingleSalary(which)), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse);
	}

	public SalaryResponse getAllSalaries(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionGirlAllSalaries()), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse);
	}

	public Response startMission(SessionResponse session, Mission mission) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionMissionStartMission(mission.getIdMission(), mission.getIdMemberMission())), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse);
	}

	public Response getFinalMissionGift(SessionResponse session) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionMissionGiveGift()), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse);
	}

	public Response claimReward(SessionResponse session, Mission mission) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionMissionClaimReward(mission.getIdMission(), mission.getIdMemberMission())), session.getCookies());
		return generateResponse(SalaryResponse.class, httpResponse);
	}

	public Response fightOpponentPlayer(SessionResponse session, BattlePlayer battlePlayer) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionBattlePlayer(battlePlayer)), session.getCookies());
		return generateResponse(Response.class, httpResponse);
	}

	public Response fightOpponentMob(SessionResponse session, BattleMob battleMob) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionBattleMob(battleMob)), session.getCookies());
		return generateResponse(Response.class, httpResponse);
	}

	public Response continueQuest(SessionResponse session, Long questId) throws ServerException, IOException {
		String url = BASE_URL + AJAX;
		HttpResponse httpResponse = RequestHelper.INSTANCE.processPostRequest(url, BodyHelper.INSTANCE.generateBody(new ActionQuestNext(questId)), session.getCookies());
		return generateResponse(Response.class, httpResponse);
	}


	private <T extends Response> T generateResponse(Class<T> responseClass, HttpResponse httpResponse) throws IOException {
		String responseBody = BodyHelper.INSTANCE.readBody(httpResponse);
		T response = JacksonHelper.INSTANCE.jsonToObject(responseClass, responseBody);
		response.setHttpResponse(httpResponse);
		return response;
	}

}
