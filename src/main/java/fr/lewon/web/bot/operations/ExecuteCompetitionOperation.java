package fr.lewon.web.bot.operations;

import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.activity.Competition;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ExecuteCompetitionOperation extends HHOperation {

	public ExecuteCompetitionOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		String activityPage = requestProcessor.getActivitiesContent(session);
		List<Competition> competitions = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage);
		runner.getBotLogger().info("Every competitions finished. Trying again in 1 day.");
		requestProcessor.getFinalMissionGift(session);
		return new Delay(1, TimeScale.DAYS);
	}

}
