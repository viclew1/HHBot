package fr.lewon.web.bot.operations;

import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.activity.Competition;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ExecuteCompetitionOperation extends Operation {

	private HHSessionManager manager;

	public ExecuteCompetitionOperation(HHSessionManager manager) {
		this.manager = manager;
	}

	@Override
	public Delay process(BotRunner runner) throws Exception {
		SessionResponse session = manager.getSession();
		String activityPage = HHRequestProcessor.INSTANCE.getActivitiesContent(session);
		List<Competition> competitions = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage);
		runner.logInfo("Every competitions finished. Trying again in 1 day.");
		HHRequestProcessor.INSTANCE.getFinalMissionGift(session);
		return new Delay(1, TimeScale.DAYS);
	}

}
