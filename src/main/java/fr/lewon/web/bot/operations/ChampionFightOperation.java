package fr.lewon.web.bot.operations;

import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.enums.Currency;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.entities.response.ChampionData;
import fr.lewon.web.bot.entities.response.DraftResponse;
import fr.lewon.web.bot.entities.response.TeamBattleResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ChampionFightOperation extends HHOperation {

	private int championId;
	
	public ChampionFightOperation(int championId) {
		this.championId = championId;
	}

	@Override
	public Delay process(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {
		
		HHSession session = sessionManager.getSession(requestProcessor);
		String championContent = requestProcessor.getChampionPageContent(session, championId);
		ChampionData championData = HtmlAnalyzer.INSTANCE.getChampionData(championContent);
		
		if (championData.getChampion().getCurrentTickets() == 0) {
			runner.getBotLogger().info("No ticket left, can't fight champion {}. Trying again in 10 hours", championId);
			return new Delay(10, TimeScale.HOURS);
		}
		
		List<Girl> team = championData.getTeam();
		for (int i = 0 ; i < championData.getFreeDrafts() ; i++) {
			Integer minPower = (int) team.stream()
					.mapToInt(Girl::getDamage)
					.average()
					.orElse(0);
			List<Integer> girlsToKeep = team.stream()
					.filter(g -> g.getDamage() > minPower)
					.sorted((g1, g2) -> g1.getDamage() - g2.getDamage())
					.map(Girl::getId)
					.collect(Collectors.toList());
			girlsToKeep = girlsToKeep.subList(0, Math.min(girlsToKeep.size(), 5));
			DraftResponse draftResp = requestProcessor.draftChampionFight(session, championId, girlsToKeep);
			team = draftResp.getTeam();
		}
		
		List<Integer> teamIds = team.stream()
				.map(Girl::getId)
				.collect(Collectors.toList());
		
		TeamBattleResponse battleResp = requestProcessor.fightChampion(session, Currency.TICKET, championId, teamIds);
		if (!battleResp.getSuccess()) {
			runner.getBotLogger().info("Can't fight champion {}. Trying again in 4 hours", championId);
			return new Delay(4, TimeScale.HOURS);
		}
		
		if (battleResp.getFnl().getAttackerEgo() <= 0) {
			runner.getBotLogger().info("Lost against champion {}. Trying again in 15 minutes", championId);
			return new Delay(15, TimeScale.MINUTES);
		}
		
		runner.getBotLogger().info("Won against champion {}. Facing him again in 1 day", championId);
		return new Delay(1, TimeScale.DAYS);
	}

}
