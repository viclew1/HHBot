package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;


public class InitHentaiHeroesBotOperation extends HHOperation {

	public InitHentaiHeroesBotOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		runner.addAction(new GirlsManagerOperation(sessionManager, requestProcessor), 0);
		runner.addAction(new FightArenaOperation(sessionManager, requestProcessor), 0);
		runner.addAction(new ExecuteMissionOperation(sessionManager, requestProcessor), 0);
		runner.addAction(new FightTowerOfFameOperation(sessionManager, requestProcessor), 0);
		runner.addAction(new FightTrollOperation(sessionManager, requestProcessor), 0);
		runner.addAction(new ContinueQuestOperation(sessionManager, requestProcessor), 0);
		runner.logInfo("Initial operations started.");
		return null;
	}

}
