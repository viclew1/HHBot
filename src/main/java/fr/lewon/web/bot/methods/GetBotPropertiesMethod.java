package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.properties.FarmProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class GetBotPropertiesMethod extends HHBotMethod {

	public GetBotPropertiesMethod(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Map<String, Object> getneededParameters() {
		return new HashMap<>();
	}

	@Override
	public String getLabel() {
		return "Get bot properties";
	}

	@Override
	protected Object doProcess(BotRunner runner, HHSessionManager manager, HHRequestProcessor requestProcessor, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		addProperty(sb, runner, FarmProperties.TROLL_WORLD_KEY);
		return sb.toString();
	}

	private void addProperty(StringBuilder sb, BotRunner runner, String key) {
		sb.append(key + " = " + runner.getBot().getGameProperties().getProperty(key));
	}

	@Override
	protected void verifyParameters(Map<String, Object> params) throws BotRunnerException {
		// No parameter to check
	}

}
