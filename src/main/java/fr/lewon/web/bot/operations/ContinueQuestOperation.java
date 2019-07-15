package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.response.UserInfos;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ContinueQuestOperation extends HHOperation {

	public ContinueQuestOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		HHSession session = sessionManager.getSession();

		String homeContent = requestProcessor.getHomeContent(session);
		UserInfos userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		Long questId = userInfos.getQuesting().getIdQuest();

		int stepCpt = 0;
		while (requestProcessor.continueQuest(session, questId).getSuccess()) {
			stepCpt ++;
		}

		runner.getBotLogger().info("Quest {} advanced {} steps. Trying again in 4 hours", questId, stepCpt);
		return new Delay(4, TimeScale.HOURS);
	}

}
