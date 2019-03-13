package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class FightArenaOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(FightArenaOperation.class);

	@Override
	public Integer process() throws Exception {
		SessionResponse session = SessionManager.INSTANCE.getSession();
		RequestProcessor.INSTANCE.getArenaContent(session);
		int cpt = 0;
		for (int i = 0 ; i <= 2 ; i++) {
			String pageContent = RequestProcessor.INSTANCE.getBattleArenaContent(session, i);
			BattlePlayer battlePlayer = HtmlAnalyzer.INSTANCE.findOpponentBattlePlayer(pageContent);
			if (battlePlayer == null) {
				continue;
			}
			if (RequestProcessor.INSTANCE.fightOpponentPlayer(session, battlePlayer).getSuccess()) {
				cpt++;
			}
		}
		LOGGER.info("{} arena fights done. Trying again in 15 minutes.", cpt);
		return 900;
	}

}
