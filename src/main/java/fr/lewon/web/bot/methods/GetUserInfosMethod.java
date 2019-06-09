package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GetUserInfosMethod implements HHBotProcessor {

	@Override
	public Map<String, Object> getNeededParameters(BotRunner runner) {
		return new HashMap<>();
	}

	@Override
	public Object process(BotRunner runner, HHSessionManager manager, HHRequestProcessor requestProcessor, Map<String, Object> params) {
		try {
			String homeContent = requestProcessor.getHomeContent(manager.getSession());
			return HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
