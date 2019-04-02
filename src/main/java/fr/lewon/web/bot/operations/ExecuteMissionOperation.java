package fr.lewon.web.bot.operations;

import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.input.others.activity.Mission;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ExecuteMissionOperation extends Operation {

	private HHSessionManager manager;

	public ExecuteMissionOperation(HHSessionManager manager) {
		this.manager = manager;
	}

	@Override
	public Delay process(BotRunner runner) throws Exception {
		SessionResponse session = manager.getSession();
		String activityPage = HHRequestProcessor.INSTANCE.getActivitiesContent(session);
		List<Mission> missions = HtmlAnalyzer.INSTANCE.getMissions(activityPage);
		missions.sort((m1, m2) -> m2.getRarity().getValue() - m1.getRarity().getValue());
		for (Mission m : missions) {
			if (m.getRemainingTime() != null && m.getRemainingTime() <= 0) {
				HHRequestProcessor.INSTANCE.claimReward(session, m);
				runner.logInfo("Mission {} claimed.", m.getIdMission());
			} else if (m.isStartable()) {
				HHRequestProcessor.INSTANCE.startMission(session, m);
				runner.logInfo("Mission {} started. Claiming it in {} seconds", m.getIdMission(), m.getDuration());
				return new Delay(m.getDuration() + 5, TimeScale.SECONDS);
			}
		}

		runner.logInfo("Every mission finished. Trying again in 2 hours.");
		HHRequestProcessor.INSTANCE.getFinalMissionGift(session);
		return new Delay(2, TimeScale.HOURS);

	}

}
