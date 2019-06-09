package fr.lewon.web.bot.operations;

import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.Response;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.UserInfos;
import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.input.others.battle.TowerOfFameOpponentPremise;
import fr.lewon.web.bot.properties.HHBotProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightTowerOfFameOperation extends HHOperation {

	public FightTowerOfFameOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		
		String homeContent = requestProcessor.getHomeContent(session);
		UserInfos userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		Integer energy = userInfos.getEnergyChallenge();
		Integer energyToKeep = Integer.parseInt(runner.getBot().getPropStore().getProperty(HHBotProperties.TOWER_ENERGY_TO_KEEP.getKey()).getValue().toString());
		Integer fightCount = energy - energyToKeep;
		
		if (fightCount <= 0) {
			runner.logInfo("Not enough energy. Trying again in 2 hour");
			return new Delay(2, TimeScale.HOURS);
		}
		
		String pageContent = requestProcessor.getTowerOfFameContent(session);
		List<TowerOfFameOpponentPremise> premises = HtmlAnalyzer.INSTANCE.findHallOfFameOpponents(pageContent);
		premises.sort((o1, o2) -> o1.getLvl() - o2.getLvl());

		int cpt = 0;
		for (TowerOfFameOpponentPremise premise : premises) {
			String battlePageContent = requestProcessor.getLeagueBattleContent(session, premise.getId());
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(battlePageContent);
			if (battlePlayer == null) {
				continue;
			}
			Response fight = null;
			while ((fight = requestProcessor.fightOpponentPlayer(session, battlePlayer)).getSuccess()) {
				if (++cpt >= fightCount) {
					runner.logInfo("{} Tower of fame fights done. Trying again in 2 hours", cpt);
					return new Delay(2, TimeScale.HOURS);
				}
			}
			if ("Not enough challenge energy.".equals(fight.getError())) {
				runner.logInfo("Stopped by error : Not enough energy - {} Tower of fame fights done. Trying again in 2 hours", cpt);
				return new Delay(2, TimeScale.HOURS);
			}
		}

		runner.logInfo("No opponent to fight. Trying again in 5 hours");
		return new Delay(5, TimeScale.HOURS);
	}

}
