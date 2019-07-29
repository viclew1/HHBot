package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;


public class InitHentaiHeroesBotOperation extends HHOperation {

	@Override
	public Delay process(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		runner.addAction(new GirlsManagerOperation());
		runner.addAction(new FightArenaOperation());
		runner.addAction(new ExecuteMissionOperation());
		runner.addAction(new FightTowerOfFameOperation());
		runner.addAction(new FightTrollOperation());
		runner.addAction(new ContinueQuestOperation());
		runner.addAction(new AutoShopOperation());
		runner.addAction(new ChampionsFightsManagerOperation());
		runner.addAction(new ExecuteCompetitionOperation());
		
		runner.getBotLogger().info("Initial operations started.");
		return null;
	}

}
