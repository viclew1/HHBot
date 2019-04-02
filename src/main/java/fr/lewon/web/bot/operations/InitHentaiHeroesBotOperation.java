package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.util.HHSessionManager;


public class InitHentaiHeroesBotOperation extends Operation {

	private HHSessionManager manager;

	public InitHentaiHeroesBotOperation(HHSessionManager manager) {
		this.manager = manager;
	}

	@Override
	public Delay process(BotRunner runner) throws Exception {
		runner.addAction(new GirlsManagerOperation(manager), 0);
		runner.addAction(new FightArenaOperation(manager), 0);
		runner.addAction(new ExecuteMissionOperation(manager), 0);
		runner.addAction(new FightTowerOfFameOperation(manager), 0);
		runner.addAction(new FightTrollOperation(manager), 0);
		runner.addAction(new ContinueQuestOperation(manager), 0);
		runner.logInfo("Initial operations started.");
		return null;
	}

}
