package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ContinueQuestOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContinueQuestOperation.class);

	private HHSessionManager manager;

	public ContinueQuestOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Delay process() throws Exception {
		SessionResponse session = manager.getSession();
		String mapContent = HHRequestProcessor.INSTANCE.getMapContent(session);
		Long questId = HtmlAnalyzer.INSTANCE.getCurrentQuestId(mapContent);
		if (questId == null) {
			LOGGER.info("No current quest. Trying again in an hour.");
			return new Delay(1, TimeScale.HOURS);
		}
		int steps = 0;
		while (HHRequestProcessor.INSTANCE.continueQuest(session, questId).getSuccess()) {
			steps ++;
		}
		LOGGER.info("Quest {} advanced {} steps. Trying again in 30 minutes", questId, steps);
		return new Delay(30, TimeScale.MINUTES);
	}

}
