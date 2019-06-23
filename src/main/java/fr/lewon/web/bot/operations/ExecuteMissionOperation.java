package fr.lewon.web.bot.operations;

import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.activity.Mission;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ExecuteMissionOperation extends HHOperation {


	public ExecuteMissionOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		String activityPage = requestProcessor.getActivitiesContent(session);
		List<Mission> missions = HtmlAnalyzer.INSTANCE.getMissions(activityPage);
		missions.sort((m1, m2) -> m2.getRarity().getValue() - m1.getRarity().getValue());
		for (Mission m : missions) {
			if (m.getRemainingTime() != null && m.getRemainingTime() <= 0) {
				requestProcessor.claimReward(session, m);
				runner.getBotLogger().info("Mission {} claimed.", m.getIdMission());
			} else if (m.isStartable()) {
				requestProcessor.startMission(session, m);
				runner.getBotLogger().info("Mission {} started. Claiming it in {} seconds", m.getIdMission(), m.getDuration());
				return new Delay(m.getDuration() + 5, TimeScale.SECONDS);
			}
		}

		runner.getBotLogger().info("Every mission finished. Trying again in 2 hours.");
		requestProcessor.getFinalMissionGift(session);
		return new Delay(2, TimeScale.HOURS);

	}

}
