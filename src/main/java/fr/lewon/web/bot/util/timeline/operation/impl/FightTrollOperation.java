package fr.lewon.web.bot.util.timeline.operation.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.properties.FarmProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.HHSessionManager;

public class FightTrollOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(FightTrollOperation.class);

	private HHSessionManager manager;

	public FightTrollOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Integer process() throws Exception {
		SessionResponse session = manager.getSession();
		String worldId = getWorldId(session);
		String worldContent = HHRequestProcessor.INSTANCE.getWorldContent(session, worldId);
		String trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent);
		if (trollId == null) {
			LOGGER.info("No troll found in world {}. Trying again in an hour.", worldId);
			return 3600;
		}
		String battleTrollContent = HHRequestProcessor.INSTANCE.getBattleTrollContent(session, trollId);
		BattleMob battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent);
		battleMob.setIdTroll(trollId);
		battleMob.setIdWorld(worldId);

		int fightCpt = 0;
		while (HHRequestProcessor.INSTANCE.fightOpponentMob(session, battleMob).getSuccess()) {
			fightCpt++;
		}
		LOGGER.info("Troll {} fought. {} fights done. Trying again in 30 minutes.", trollId, fightCpt);
		return 1800;
	}

	private String getWorldId(SessionResponse session) throws ServerException, IOException {
		String preferedWorldId = getRunner().getBot().getGameProperties().getProperty(FarmProperties.TROLL_WORLD_KEY);
		if (preferedWorldId != null) {
			return preferedWorldId;
		}
		String mapContent = HHRequestProcessor.INSTANCE.getMapContent(session);
		return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent);
	}

}
