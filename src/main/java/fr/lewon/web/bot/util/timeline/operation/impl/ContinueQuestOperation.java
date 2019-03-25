package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.HHSessionManager;

public class ContinueQuestOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContinueQuestOperation.class);

	private HHSessionManager manager;

	public ContinueQuestOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Integer process() throws Exception {
		SessionResponse session = manager.getSession();
		String mapContent = HHRequestProcessor.INSTANCE.getMapContent(session);
		Long questId = HtmlAnalyzer.INSTANCE.getCurrentQuestId(mapContent);
		if (questId == null) {
			LOGGER.info("No current quest. Trying again in an hour.");
			return 3600;
		}
		int steps = 0;
		while (HHRequestProcessor.INSTANCE.continueQuest(session, questId).getSuccess()) {
			steps ++;
		}
		LOGGER.info("Quest {} advanced {} steps. Trying again in 30 minutes", questId, steps);
		return 1800;
	}

}
