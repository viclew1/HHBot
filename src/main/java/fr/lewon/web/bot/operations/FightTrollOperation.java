package fr.lewon.web.bot.operations;

import java.io.IOException;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.properties.FarmProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightTrollOperation extends Operation {

	private HHSessionManager manager;

	public FightTrollOperation(HHSessionManager manager) {
		this.manager = manager;
	}

	@Override
	public Delay process(BotRunner runner) throws Exception {
		SessionResponse session = manager.getSession();
		String worldId = getWorldId(runner, session);
		String worldContent = HHRequestProcessor.INSTANCE.getWorldContent(session, worldId);
		String trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent);
		if (trollId == null) {
			runner.logInfo("No troll found in world {}. Trying again in 1 hour.", worldId);
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
		runner.logInfo("Troll {} fought. {} fights done. Trying again in 2 hours.", trollId, fightCpt);
		return new Delay(2, TimeScale.HOURS);
	}

	private String getWorldId(BotRunner runner, SessionResponse session) throws ServerException, IOException {
		String preferedWorldId = runner.getBot().getGameProperties().getProperty(FarmProperties.TROLL_WORLD_KEY);
		if (preferedWorldId != null) {
			return preferedWorldId;
		}
		String mapContent = HHRequestProcessor.INSTANCE.getMapContent(session);
		return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent);
	}

}
