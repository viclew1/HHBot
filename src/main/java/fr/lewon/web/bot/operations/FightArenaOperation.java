package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.response.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightArenaOperation extends HHOperation {

	public FightArenaOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		requestProcessor.getArenaContent(session);
		int cpt = 0;
		for (int i = 0 ; i <= 2 ; i++) {
			String pageContent = requestProcessor.getBattleArenaContent(session, i);
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(pageContent);
			if (battlePlayer == null) {
				continue;
			}
			if (requestProcessor.fightOpponentPlayer(session, battlePlayer).getSuccess()) {
				cpt++;
			}
		}
		runner.getBotLogger().info("{} arena fights done. Trying again in 15 minutes.", cpt);
		return new Delay(15, TimeScale.MINUTES);
	}

}
