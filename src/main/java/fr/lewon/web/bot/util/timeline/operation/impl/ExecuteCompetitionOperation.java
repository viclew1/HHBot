package fr.lewon.web.bot.util.timeline.operation.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.entities.input.others.activity.Competition;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.SessionManager;

public class ExecuteCompetitionOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteCompetitionOperation.class);

	private SessionManager manager;

	public ExecuteCompetitionOperation(BotRunner runner, SessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	@Override
	public Integer process() throws Exception {
		SessionResponse session = manager.getSession();
		String activityPage = HHRequestProcessor.INSTANCE.getActivitiesContent(session);
		List<Competition> competitions = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage);
		LOGGER.info("Every competitions finished. Trying again in 23:30 hours.");
		HHRequestProcessor.INSTANCE.getFinalMissionGift(session);
		return 3600 * 23 + 1800;
	}

}
