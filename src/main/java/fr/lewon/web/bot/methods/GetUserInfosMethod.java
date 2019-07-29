package fr.lewon.web.bot.methods;

import java.util.ArrayList;
import java.util.List;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GetUserInfosMethod extends HHBotProcessor {

	public GetUserInfosMethod(String id, String label) {
		super(id, label, new ArrayList<>());
	}

	@Override
	protected List<BotPropertyDescriptor> getSpecificParamBuilders(BotRunner runner) {
		return new ArrayList<>();
	}

	@Override
	protected Object doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor,
			BotPropertyStore params) throws BotRunnerException {
		
		try {
			String homeContent = requestProcessor.getHomeContent(sessionManager.getSession(requestProcessor));
			return HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
