package fr.lewon.web.bot.util.timeline.operation.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.input.others.activity.Mission;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class ExecuteMissionOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteMissionOperation.class);

	@Override
	public Integer process() throws Exception {
		SessionResponse session = SessionManager.INSTANCE.getSession();
		String activityPage = RequestProcessor.INSTANCE.getActivitiesContent(session);
		List<Mission> missions = HtmlAnalyzer.INSTANCE.getMissions(activityPage);
		missions.sort((m1, m2) -> m2.getRarity().getValue() - m1.getRarity().getValue());
		for (Mission m : missions) {
			if (m.getRemainingTime() != null && m.getRemainingTime() <= 0) {
				RequestProcessor.INSTANCE.claimReward(session, m);
				LOGGER.info("Mission {} claimed.", m.getIdMission());
			} else if (m.isStartable()) {
				RequestProcessor.INSTANCE.startMission(session, m);
				LOGGER.info("Mission {} started. Claiming it in {} seconds", m.getIdMission(), m.getDuration());
				return m.getDuration() + 5;
			}
		}

		LOGGER.info("Every mission finished. Trying again in 2 hours.");
		RequestProcessor.INSTANCE.getFinalMissionGift(session);
		return 7200;

	}

}
