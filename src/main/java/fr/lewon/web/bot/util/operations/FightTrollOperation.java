package fr.lewon.web.bot.util.operations;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.properties.FarmProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightTrollOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(FightTrollOperation.class);

	private HHSessionManager manager;

	public FightTrollOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Delay process() throws Exception {
		SessionResponse session = manager.getSession();
		String worldId = getWorldId(session);
		String worldContent = HHRequestProcessor.INSTANCE.getWorldContent(session, worldId);
		String trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent);
		if (trollId == null) {
			LOGGER.info("No troll found in world {}. Trying again in 1 hour.", worldId);
			return new Delay(1, TimeScale.HOURS);
		}
		String battleTrollContent = HHRequestProcessor.INSTANCE.getBattleTrollContent(session, trollId);
		BattleMob battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent);
		battleMob.setIdTroll(trollId);
		battleMob.setIdWorld(worldId);

		int fightCpt = 0;
		while (HHRequestProcessor.INSTANCE.fightOpponentMob(session, battleMob).getSuccess()) {
			fightCpt++;
		}
		LOGGER.info("Troll {} fought. {} fights done. Trying again in 2 hours.", trollId, fightCpt);
		return new Delay(2, TimeScale.HOURS);
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
