package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.util.SessionManager;


public class InitHentaiHeroesBotOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitHentaiHeroesBotOperation.class);

	private SessionManager manager;

	public InitHentaiHeroesBotOperation(BotRunner runner, SessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Integer process() throws Exception {
		getRunner().addAction(new GirlsHarvesterManagerOperation(getRunner(), manager), 0);
		getRunner().addAction(new FightArenaOperation(getRunner(), manager), 0);
		getRunner().addAction(new ExecuteMissionOperation(getRunner(), manager), 0);
		getRunner().addAction(new FightTowerOfFameOperation(getRunner(), manager), 0);
		getRunner().addAction(new FightTrollOperation(getRunner(), manager), 0);
		getRunner().addAction(new ContinueQuestOperation(getRunner(), manager), 0);
		LOGGER.info("Initial operations started.");
		return null;
	}

}
