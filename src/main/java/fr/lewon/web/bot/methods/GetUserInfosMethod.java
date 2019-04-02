package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GetUserInfosMethod extends AbstractBotMethod {

	private HHSessionManager manager;

	public GetUserInfosMethod(HHSessionManager manager) {
		this.manager = manager;
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
	public Object doProcess(BotRunner runner, Map<String, Object> params) {
		try {
			String homeContent = HHRequestProcessor.INSTANCE.getHomeContent(manager.getSession());
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
