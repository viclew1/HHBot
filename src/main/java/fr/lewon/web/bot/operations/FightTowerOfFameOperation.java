package fr.lewon.web.bot.operations;

import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.Response;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.input.others.battle.TowerOfFameOpponentPremise;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightTowerOfFameOperation extends Operation {

	private HHSessionManager manager;

	public FightTowerOfFameOperation(HHSessionManager manager) {
		this.manager = manager;
	}

	@Override
	public Delay process(BotRunner runner) throws Exception {
		SessionResponse session = manager.getSession();
		String pageContent = HHRequestProcessor.INSTANCE.getTowerOfFameContent(session);
		List<TowerOfFameOpponentPremise> premises = HtmlAnalyzer.INSTANCE.findHallOfFameOpponents(pageContent);
		premises.sort((o1, o2) -> o1.getLvl() - o2.getLvl());

		int cpt = 0;
		for (TowerOfFameOpponentPremise premise : premises) {
			String battlePageContent = HHRequestProcessor.INSTANCE.getLeagueBattleContent(session, premise.getId());
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(battlePageContent);
			if (battlePlayer == null) {
				continue;
			}
			Response fight = null;
			while ((fight = HHRequestProcessor.INSTANCE.fightOpponentPlayer(session, battlePlayer)).getSuccess()) {
				cpt++;
			}
			if ("Not enough challenge energy.".equals(fight.getError())) {
				runner.logInfo("{} Tower of fame fights done. Trying again in 30 minutes", cpt);
				return new Delay(30, TimeScale.MINUTES);
			}
		}

		runner.logInfo("No opponent to fight. Trying again in 1 hour");
		return new Delay(1, TimeScale.HOURS);
	}

}
