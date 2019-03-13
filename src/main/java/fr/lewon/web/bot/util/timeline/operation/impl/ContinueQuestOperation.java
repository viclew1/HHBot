package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class ContinueQuestOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContinueQuestOperation.class);

	@Override
	public Integer process() throws Exception {
		SessionResponse session = SessionManager.INSTANCE.getSession();
		String mapContent = RequestProcessor.INSTANCE.getMapContent(session);
		Long questId = HtmlAnalyzer.INSTANCE.getCurrentQuestId(mapContent);
		if (questId == null) {
			LOGGER.info("No current quest. Trying again in an hour.");
			return 3600;
		}
		int steps = 0;
		while (RequestProcessor.INSTANCE.continueQuest(session, questId).getSuccess()) {
			steps ++;
		}
		LOGGER.info("Quest {} advanced {} steps. Trying again in 30 minutes", questId, steps);
		return 1800;
	}

}
