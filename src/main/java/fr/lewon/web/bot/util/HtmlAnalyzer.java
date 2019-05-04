package fr.lewon.web.bot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lewon.bot.http.util.JacksonHelper;
import fr.lewon.web.bot.entities.UserInfos;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.entities.input.others.activity.Competition;
import fr.lewon.web.bot.entities.input.others.activity.Mission;
import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.input.others.battle.TowerOfFameOpponentPremise;
import fr.lewon.web.bot.entities.quests.QuestStep;

public enum HtmlAnalyzer {

	INSTANCE;

	private HtmlAnalyzer() {}

	public QuestStep[] getQuestSteps(String questContent) throws IOException {
		String regex = "Q.steps = (\\[.*?\\]);";
		Matcher matcher = matchPattern(questContent, regex);
		if (!matcher.find()) {
			return null;
		}
		String json = matcher.group(1).replace("\"cost\":[]", "\"cost\":{}");
		return JacksonHelper.INSTANCE.jsonToObject(QuestStep[].class, json);
	}

	public List<Girl> findAllGirls(String haremContent) throws IOException {
		List<Girl> girls = new ArrayList<>();
		String regex = "girlsDataList\\['[0-9]+'\\] = (\\{.*?};)";
		Matcher matcher = matchPattern(haremContent, regex);

		while (matcher.find()) {
			Girl girl = JacksonHelper.INSTANCE.jsonToObject(Girl.class, matcher.group(1));
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

		return JacksonHelper.INSTANCE.jsonToObject(UserInfos.class, matcher.group(1));
	}

	public BattlePlayer findOpponentBattlePlayer(String content) throws IOException {
		String regex = "hh_battle_players =.*?\\{.*?},.*?(\\{.*?})";
		Matcher matcher = matchPattern(content, regex);

		if (!matcher.find()) {
			return null;
		}
		String battlePlayerStr = matcher.group(1);
		return JacksonHelper.INSTANCE.jsonToObject(BattlePlayer.class, battlePlayerStr);
	}

	public BattleMob findOpponentBattleMob(String battleTrollContent) throws IOException {
		String regex = "hh_battle_players =.*?\\{.*?},.*?(\\{.*?})";
		Matcher matcher = matchPattern(battleTrollContent, regex);

		if (!matcher.find()) {
			return null;
		}
		String battlePlayerStr = matcher.group(1);
		return JacksonHelper.INSTANCE.jsonToObject(BattleMob.class, battlePlayerStr);
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
		content = flattenContent(content);
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

			Mission mission = JacksonHelper.INSTANCE.jsonToObject(Mission.class, missionBody);
			mission.setRarity(rarity);
			mission.setStartable(startable);
			missions.add(mission);
		}
		return missions;
	}

	public List<Competition> getCompetitions(String activityPage) {
		List<Competition> competitions = new ArrayList<>();
		// TODO récupérer les compétitions finies
		return competitions;
	}

}
