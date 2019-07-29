package fr.lewon.web.bot.operations;

import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.battle.BattlePlayer;
import fr.lewon.web.bot.entities.battle.TowerOfFameOpponentPremise;
import fr.lewon.web.bot.entities.response.UserInfos;
import fr.lewon.web.bot.properties.HHBotProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightTowerOfFameOperation extends HHOperation {

	@Override
	public Delay process(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		HHSession session = sessionManager.getSession(requestProcessor);
		
		String homeContent = requestProcessor.getHomeContent(session);
		UserInfos userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		Integer energy = userInfos.getEnergyChallenge();
		Integer energyToKeep = (Integer) runner.getBot().getPropStore().get(HHBotProperties.TOWER_ENERGY_TO_KEEP.getDescriptor().getKey());
		Integer fightCount = energy - energyToKeep;
		
		if (fightCount <= 0) {
			runner.getBotLogger().info("Not enough energy. Trying again in 2 hour");
			return new Delay(2, TimeScale.HOURS);
		}
		
		String pageContent = requestProcessor.getTowerOfFameContent(session);
		List<TowerOfFameOpponentPremise> premises = HtmlAnalyzer.INSTANCE.findHallOfFameOpponents(pageContent).stream()
				.filter(p -> p.getNbChallengesPlayed() < 3)
				.sorted((o1, o2) -> o1.getLvl() - o2.getLvl())
				.collect(Collectors.toList());

		int cpt = 0;
		for (TowerOfFameOpponentPremise premise : premises) {
			String battlePageContent = requestProcessor.getLeagueBattleContent(session, premise.getId());
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(battlePageContent);
			if (battlePlayer == null) {
				continue;
			}
			int i = 0;
			while (i++ < 3 && requestProcessor.fightOpponentPlayer(session, battlePlayer).getSuccess()) {
				if (++cpt >= fightCount) {
					runner.getBotLogger().info("{} Tower of fame fights done. Trying again in 2 hours", cpt);
					return new Delay(2, TimeScale.HOURS);
				}
			}
		}

		runner.getBotLogger().info("No opponent to fight. Trying again in 5 hours");
		return new Delay(5, TimeScale.HOURS);
	}

}
