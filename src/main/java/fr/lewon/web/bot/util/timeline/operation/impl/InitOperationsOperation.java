package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.util.timeline.TimeLineManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class InitOperationsOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitOperationsOperation.class);

	@Override
	public Integer process() throws Exception {
		TimeLineManager.INSTANCE.addAction(new GirlsHarvesterManagerOperation(), 0);
		TimeLineManager.INSTANCE.addAction(new FightArenaOperation(), 0);
		TimeLineManager.INSTANCE.addAction(new ExecuteMissionOperation(), 0);
		TimeLineManager.INSTANCE.addAction(new FightTowerOfFameOperation(), 0);
		TimeLineManager.INSTANCE.addAction(new FightTrollOperation(), 0);
		TimeLineManager.INSTANCE.addAction(new ContinueQuestOperation(), 0);
		LOGGER.info("Initial operations started.");
		return null;
	}

}
