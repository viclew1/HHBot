package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightArenaOperation extends Operation {

	private HHSessionManager manager;

	public FightArenaOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Delay process() throws Exception {
		SessionResponse session = manager.getSession();
		HHRequestProcessor.INSTANCE.getArenaContent(session);
		int cpt = 0;
		for (int i = 0 ; i <= 2 ; i++) {
			String pageContent = HHRequestProcessor.INSTANCE.getBattleArenaContent(session, i);
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(pageContent);
			if (battlePlayer == null) {
				continue;
			}
			if (HHRequestProcessor.INSTANCE.fightOpponentPlayer(session, battlePlayer).getSuccess()) {
				cpt++;
			}
		}
		getRunner().logInfo("{} arena fights done. Trying again in 15 minutes.", cpt);
		return new Delay(15, TimeScale.MINUTES);
	}

}
