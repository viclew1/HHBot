package fr.lewon.web.bot.util.timeline.operation.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.exceptions.ServerException;
import fr.lewon.web.bot.properties.FarmProperties;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class FightTrollOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(FightTrollOperation.class);

	@Override
	public Integer process() throws Exception {
		SessionResponse session = SessionManager.INSTANCE.getSession();
		String worldId = getWorldId(session);
		String worldContent = RequestProcessor.INSTANCE.getWorldContent(session, worldId);
		String trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent);
		String battleTrollContent = RequestProcessor.INSTANCE.getBattleTrollContent(session, trollId);
		BattleMob battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent);
		battleMob.setIdTroll(trollId);
		battleMob.setIdWorld(worldId);

		int fightCpt = 0;
		while (RequestProcessor.INSTANCE.fightOpponentMob(session, battleMob).getSuccess()) {
			fightCpt++;
		}
		LOGGER.info("Troll {} fought. {} fights done. Trying again in 30 minutes.", trollId, fightCpt);
		return 1800;
	}

	private String getWorldId(SessionResponse session) throws ServerException, IOException {
		String preferedWorldId = FarmProperties.INSTANCE.getWorldId();
		if (preferedWorldId != null) {
			return preferedWorldId;
		}
		String mapContent = RequestProcessor.INSTANCE.getMapContent(session);
		return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent);
	}

}
