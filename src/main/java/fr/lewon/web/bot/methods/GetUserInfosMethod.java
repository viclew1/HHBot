package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GetUserInfosMethod extends HHBotMethod {

	public GetUserInfosMethod(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Map<String, Object> getneededParameters() {
		return new HashMap<>();
	}

	@Override
	public String getLabel() {
		return "Get user infos";
	}

	@Override
	protected Object doProcess(BotRunner runner, HHSessionManager manager, HHRequestProcessor requestProcessor, Map<String, Object> params) {
		try {
			String homeContent = requestProcessor.getHomeContent(manager.getSession());
			return HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	protected void verifyParameters(Map<String, Object> params) throws BotRunnerException {
		// No parameter to check
	}

}
