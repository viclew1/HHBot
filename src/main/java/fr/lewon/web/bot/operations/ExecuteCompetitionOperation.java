package fr.lewon.web.bot.operations;

import java.util.ArrayList;
import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ExecuteCompetitionOperation extends HHOperation {

	@Override
	public Delay process(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		HHSession session = sessionManager.getSession(requestProcessor);
		String activityPage = requestProcessor.getActivitiesContent(session);
		List<Integer> competitionsIds = HtmlAnalyzer.INSTANCE.getCompetitions(activityPage);
		List<Integer> endedCompetitions = new ArrayList<>();
		for (Integer id : competitionsIds) {
			if (requestProcessor.collectCompetitionRewards(id, session).getSuccess()) {
				endedCompetitions.add(id);
			}
		}
		Integer secondsUntilNextCompetition = HtmlAnalyzer.INSTANCE.getSecondsUntilNextCompetition(activityPage);
		runner.getBotLogger().info("Competitions finished : {}. Trying again in {} seconds.", endedCompetitions, secondsUntilNextCompetition);
		return new Delay(secondsUntilNextCompetition, TimeScale.SECONDS);
	}

}
