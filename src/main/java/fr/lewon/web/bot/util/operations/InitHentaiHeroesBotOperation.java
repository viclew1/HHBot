package fr.lewon.web.bot.util.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.util.HHSessionManager;


public class InitHentaiHeroesBotOperation extends Operation {

	private HHSessionManager manager;

	public InitHentaiHeroesBotOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Delay process() throws Exception {
		getRunner().addAction(new GirlsManagerOperation(getRunner(), manager), 0);
		getRunner().addAction(new FightArenaOperation(getRunner(), manager), 0);
		getRunner().addAction(new ExecuteMissionOperation(getRunner(), manager), 0);
		getRunner().addAction(new FightTowerOfFameOperation(getRunner(), manager), 0);
		getRunner().addAction(new FightTrollOperation(getRunner(), manager), 0);
		getRunner().addAction(new ContinueQuestOperation(getRunner(), manager), 0);
		getRunner().logInfo("Initial operations started.");
		return null;
	}

}
