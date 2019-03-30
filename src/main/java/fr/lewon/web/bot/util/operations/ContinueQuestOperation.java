package fr.lewon.web.bot.util.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ContinueQuestOperation extends Operation {

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
			getRunner().logInfo("No current quest. Trying again in an hour.");
			return new Delay(1, TimeScale.HOURS);
		}
		int steps = 0;
		while (HHRequestProcessor.INSTANCE.continueQuest(session, questId).getSuccess()) {
			steps ++;
		}
		getRunner().logInfo("Quest {} advanced {} steps. Trying again in 2 hours", questId, steps);
		return new Delay(2, TimeScale.HOURS);
	}

}
