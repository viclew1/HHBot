package fr.lewon.web.bot.util.timeline.operation.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.input.others.battle.TowerOfFameOpponentPremise;
import fr.lewon.web.bot.entities.output.Response;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class FightTowerOfFameOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(FightTowerOfFameOperation.class);

	@Override
	public Integer process() throws Exception {
		SessionResponse session = SessionManager.INSTANCE.getSession();
		String pageContent = RequestProcessor.INSTANCE.getTowerOfFameContent(session);
		List<TowerOfFameOpponentPremise> premises = HtmlAnalyzer.INSTANCE.findHallOfFameOpponents(pageContent);
		premises.sort((o1, o2) -> o1.getLvl() - o2.getLvl());

		int cpt = 0;
		for (TowerOfFameOpponentPremise premise : premises) {
			String battlePageContent = RequestProcessor.INSTANCE.getLeagueBattleContent(session, premise.getId());
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(battlePageContent);
			if (battlePlayer == null) {
				continue;
			}
			Response fight = null;
			while ((fight = RequestProcessor.INSTANCE.fightOpponentPlayer(session, battlePlayer)).getSuccess()) {
				cpt++;
			}
			if ("Not enough challenge energy.".equals(fight.getError())) {
				LOGGER.info("{} Tower of fame fights done. Trying again in 30 minutes", cpt);
				return 1800;
			}
		}

		LOGGER.info("No opponent to fight. Trying again in 1 hour");
		return 3600;
	}

}
