package fr.lewon.web.bot.util.operations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.input.others.activity.Competition;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ExecuteCompetitionOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteCompetitionOperation.class);

	private HHSessionManager manager;

	public ExecuteCompetitionOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Delay process() throws Exception {
		SessionResponse session = manager.getSession();
		String activityPage = HHRequestProcessor.INSTANCE.getActivitiesContent(session);
		List<Competition> competitions = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage);
		LOGGER.info("Every competitions finished. Trying again in 1 day.");
		HHRequestProcessor.INSTANCE.getFinalMissionGift(session);
		return new Delay(1, TimeScale.DAYS);
	}

}
