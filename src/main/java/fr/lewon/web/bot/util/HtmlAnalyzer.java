package fr.lewon.web.bot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lewon.bot.http.util.JsonHelper;
import fr.lewon.web.bot.entities.activities.Mission;
import fr.lewon.web.bot.entities.battle.BattleMob;
import fr.lewon.web.bot.entities.battle.BattlePlayer;
import fr.lewon.web.bot.entities.battle.TowerOfFameOpponentPremise;
import fr.lewon.web.bot.entities.champions.ChampionPremise;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.entities.quests.QuestStep;
import fr.lewon.web.bot.entities.response.ChampionData;
import fr.lewon.web.bot.entities.response.UserInfos;
import fr.lewon.web.bot.entities.shop.Item;
import fr.lewon.web.bot.entities.shop.Shop;

public enum HtmlAnalyzer {

	INSTANCE;

	private HtmlAnalyzer() {}

	private JsonHelper jsonHelper = new JsonHelper();

	public List<Integer> getCompetitions(String activityPage) throws IOException {
		String regex = "<div class=\"contest\" id_contest=\"([0-9]+)\">.*?<div class=\"(.*?)\"";
		Matcher matcher = matchPattern(activityPage, regex);
		List<Integer> competitions = new ArrayList<>();
		while (matcher.find()) {
			String divEndedClass = matcher.group(2);
			if ("contest_header ended".equals(divEndedClass)) {
				competitions.add(Integer.parseInt(matcher.group(1)));
			}
		}
		return competitions;
	}

	public ChampionData getChampionData(String championContent) throws IOException {
		String regex = "var championData = (\\{.*?});";
		Matcher matcher = matchPattern(championContent, regex);
		if (matcher.find()) {
			return jsonHelper.jsonToObject(ChampionData.class, matcher.group(1));
		}
		return null;
	}

	public List<ChampionPremise> getChampionsIds(String championsMapContent) {
		String regex = "<a href=\"champions/([0-9]+)\" class=\"champion-lair\""
				+ ".*?"
				+ "<div class=\"champion-lair-name map-label-link\">"
				+ "(.*?)"
				+ "</div>";
		Matcher matcher = matchPattern(championsMapContent, regex);
		List<ChampionPremise> premises = new ArrayList<>();
		while (matcher.find()) {
			int id = Integer.parseInt(matcher.group(1));
			int waitTime = getWaitTime(matcher.group(2));
			premises.add(new ChampionPremise(id, waitTime));
		}
		return premises;
	}

	private int getWaitTime(String championLairContent) {
		String regex = "<div rel=\"timer\" timer=\"([0-9]+)\">";
		Matcher matcher = matchPattern(championLairContent, regex);
		if (matcher.find()) {
			Long timer = Long.parseLong(matcher.group(1));
			Integer waitTime = (int) (timer - System.currentTimeMillis() / 1000);
			return waitTime < 0 ? 0 : waitTime;
		}
		return 0;
	}


	public QuestStep[] getQuestSteps(String questContent) throws IOException {
		String regex = "Q.steps = (\\[.*?\\]);";
		Matcher matcher = matchPattern(questContent, regex);
		if (!matcher.find()) {
			return null;
		}
		String json = matcher.group(1).replace("\"cost\":[]", "\"cost\":{}");
		return jsonHelper.jsonToObject(QuestStep[].class, json);
	}

	public List<Girl> findAllGirls(String haremContent) throws IOException {
		List<Girl> girls = new ArrayList<>();
		String regex = "girlsDataList\\['[0-9]+'\\] = (\\{.*?};)";
		Matcher matcher = matchPattern(haremContent, regex);

		while (matcher.find()) {
			Girl girl = jsonHelper.jsonToObject(Girl.class, matcher.group(1));
			girls.add(girl);
		}
		return girls;
	}

	public UserInfos getPlayerInfos(String homeContent) throws IOException {
		String regex = "Hero.infos = (\\{.*?};)";
		Matcher matcher = matchPattern(homeContent, regex);

		if (!matcher.find()) {
			return null;
		}

		return jsonHelper.jsonToObject(UserInfos.class, matcher.group(1));
	}

	public BattlePlayer findOpponentBattlePlayer(String content) throws IOException {
		String regex = "hh_battle_players =.*?\\{.*?},.*?(\\{.*?})";
		Matcher matcher = matchPattern(content, regex);

		if (!matcher.find()) {
			return null;
		}
		String battlePlayerStr = matcher.group(1);
		return jsonHelper.jsonToObject(BattlePlayer.class, battlePlayerStr);
	}

	public BattleMob findOpponentBattleMob(String battleTrollContent) throws IOException {
		String regex = "hh_battle_players =.*?\\{.*?},.*?(\\{.*?})";
		Matcher matcher = matchPattern(battleTrollContent, regex);

		if (!matcher.find()) {
			return null;
		}
		String battlePlayerStr = matcher.group(1);
		return jsonHelper.jsonToObject(BattleMob.class, battlePlayerStr);
	}

	public String getCurrentWorldId(String mapContent) {
		String regex = "<a class=\"link-world\" href=\"/world/([0-9])\"";
		Matcher matcher = matchPattern(mapContent, regex);
		String lastWorldId = null;
		while (matcher.find()) {
			lastWorldId = matcher.group(1);
		}
		return lastWorldId;
	}

	public String getTrollId(String worldContent) {
		String regex = "id_troll=([0-9])";
		Matcher matcher = matchPattern(worldContent, regex);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public Integer getNextStock(String shopContent) {
		String regex = "<span rel=\"count\" time=\"([0-9]+)\">";
		Matcher matcher = matchPattern(shopContent, regex);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		return null;
	}

	public Shop getShop(String shopContent) throws IOException {
		String regex = "<div tab class=\"armor\" type=\"armor\">(.*?)<button rel=\"buy\"";
		Matcher matcher = matchPattern(shopContent, regex);
		if (matcher.find()) {
			String subShopContent = matcher.group(1)
					.replace("&quot;", "\"")
					.replace("data-d=", "\ndata-d=");
			List<Item> books = getBooks(subShopContent);
			List<Item> gifts = getGifts(subShopContent);
			return new Shop(books, gifts);
		}
		return null;
	}

	private List<Item> getGifts(String subShopContent) throws IOException {
		String regex = "data-d=\"(\\{.*?\"type\":\"gift\".*?})\">";
		Matcher matcher = matchPattern(subShopContent, regex, false);
		List<Item> gifts = new ArrayList<>();
		while (matcher.find()) {
			gifts.add(jsonHelper.jsonToObject(Item.class, matcher.group(1)));
		}
		return gifts;
	}

	private List<Item> getBooks(String subShopContent) throws IOException {
		String regex = "data-d=\"(\\{.*?\"type\":\"potion\".*?})\">";
		Matcher matcher = matchPattern(subShopContent, regex, false);
		List<Item> books = new ArrayList<>();
		while (matcher.find()) {
			books.add(jsonHelper.jsonToObject(Item.class, matcher.group(1)));
		}
		return books;
	}

	public List<TowerOfFameOpponentPremise> findHallOfFameOpponents(String content) {
		String regexLeadTable = "<div class=\"lead_table_view\">.*?<tbody class=\"leadTable\" sorting_table>(.*?)<\\/div>";
		Matcher matcherLeadTable = matchPattern(content, regexLeadTable);
		if (!matcherLeadTable.find()) {
			return new ArrayList<>();
		}
		content = matcherLeadTable.group(1);

		String regex = "<tr sorting_id=\"(.*?)\".*?<span class=\"nickname\">(.*?)<\\/span>.*?<td>.*?([0-9]+).*?<td>([0-9])\\/3.*?<\\/tr>";
		Matcher matcher = matchPattern(content, regex);

		List<TowerOfFameOpponentPremise> premises = new ArrayList<>();
		while (matcher.find()) {
			int timesFought = Integer.parseInt(matcher.group(4));
			if (timesFought == 3) {
				continue;
			}
			String id = matcher.group(1);
			String nickName = matcher.group(2);
			int lvl = Integer.parseInt(matcher.group(3));
			premises.add(new TowerOfFameOpponentPremise(id, nickName, lvl));
		}

		return premises;
	}

	private Matcher matchPattern(String content, String regex) {
		return matchPattern(content, regex, true);
	}

	private Matcher matchPattern(String content, String regex, boolean flatten) {
		content = flatten ? flattenContent(content) : content;
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(content);
	}

	private String flattenContent(String content) {
		return content.replaceAll("[\r\n]+", " ");
	}

	public List<Mission> getMissions(String activityPage) throws IOException {
		List<Mission> missions = new ArrayList<>();
		String regexMissionsWrap = "<div class=\"missions_wrap\">(.*?)<script type=\"text/javascript\"";
		Matcher matcherMissionsWrap = matchPattern(activityPage, regexMissionsWrap);
		if (!matcherMissionsWrap.find()) {
			return missions;
		}
		String missionsWrapContent = matcherMissionsWrap.group(1);
		String missionBodyRegex = "<div class=\"mission_object sub_block (.*?)\" data-d='(\\{.*?})'.*?<button rel=\"mission_start\" class=\"blue_text_button\" (.*?)>";
		Matcher matcherMissions = matchPattern(missionsWrapContent, missionBodyRegex);
		while (matcherMissions.find()) {
			String rarity = matcherMissions.group(1);
			String missionBody = matcherMissions.group(2);
			String displayStart = matcherMissions.group(3);
			boolean startable = displayStart == null || "".equals(displayStart);

			Mission mission = jsonHelper.jsonToObject(Mission.class, missionBody);
			mission.setRarity(rarity);
			mission.setStartable(startable);
			missions.add(mission);
		}
		return missions;
	}


}
