package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.properties.FarmProperties;

public class GetBotPropertiesMethod extends AbstractBotMethod {

	@Override
	public Map<String, Object> getneededParameters() {
		return new HashMap<>();
	}

	@Override
	public String getLabel() {
		return "Get bot properties";
	}

	@Override
	public Object doProcess(BotRunner runner, Map<String, Object> params) {
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
