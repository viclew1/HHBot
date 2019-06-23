package fr.lewon.web.bot.operations;

import java.io.IOException;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.Response;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.UserInfos;
import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.properties.HHBotProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class FightTrollOperation extends HHOperation {

	public FightTrollOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();

		String homeContent = requestProcessor.getHomeContent(session);
		UserInfos userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		Integer energy = userInfos.getEnergyFight();
		Integer energyToKeep = (Integer) runner.getBot().getPropStore().get(HHBotProperties.FIGHT_ENERGY_TO_KEEP.getDescriptor().getKey());

		String worldId = getWorldId(runner, requestProcessor, session);
		if (worldId == null) {
			runner.getBotLogger().info("No world found. Trying again in 1 hour.");
			return new Delay(1, TimeScale.HOURS);
		}

		String worldContent = requestProcessor.getWorldContent(session, worldId);
		String trollId = HtmlAnalyzer.INSTANCE.getTrollId(worldContent);
		if (trollId == null) {
			runner.getBotLogger().info("No troll found in world {}. Trying again in 1 hour.", worldId);
			return new Delay(1, TimeScale.HOURS);
		}
		String battleTrollContent = requestProcessor.getBattleTrollContent(session, trollId);
		BattleMob battleMob = HtmlAnalyzer.INSTANCE.findOpponentBattleMob(battleTrollContent);
		battleMob.setIdTroll(trollId);
		battleMob.setIdWorld(worldId);

		int fightCpt = 0;
		for (int i = energyToKeep ; i < energy ; i++) {
			Response response = requestProcessor.fightOpponentMob(session, battleMob);
			if (!response.getSuccess()) {
				break;
			}
			fightCpt++;
		}
		runner.getBotLogger().info("Troll {} fought. {} fights done. Trying again in 2 hours.", trollId, fightCpt);
		return new Delay(2, TimeScale.HOURS);
	}

	private String getWorldId(BotRunner runner, HHRequestProcessor requestProcessor, SessionResponse session) throws ServerException, IOException {
		Integer preferedWorldId = (Integer) runner.getBot().getPropStore().get(HHBotProperties.TROLL_WORLD.getDescriptor().getKey());
		if (preferedWorldId != null) {
			return String.valueOf(preferedWorldId);
		}
		String mapContent = requestProcessor.getMapContent(session);
		return HtmlAnalyzer.INSTANCE.getCurrentWorldId(mapContent);
	}

}