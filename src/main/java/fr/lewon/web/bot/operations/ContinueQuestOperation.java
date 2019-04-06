package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ContinueQuestOperation extends HHOperation {

	public ContinueQuestOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		String mapContent = requestProcessor.getMapContent(session);
		Long questId = HtmlAnalyzer.INSTANCE.getCurrentQuestId(mapContent);
		if (questId == null) {
			runner.logInfo("No current quest. Trying again in an hour.");
			return new Delay(1, TimeScale.HOURS);
		}
		int steps = 0;
		while (requestProcessor.continueQuest(session, questId).getSuccess()) {
			steps ++;
		}
		runner.logInfo("Quest {} advanced {} steps. Trying again in 2 hours", questId, steps);
		return new Delay(2, TimeScale.HOURS);
	}

}
