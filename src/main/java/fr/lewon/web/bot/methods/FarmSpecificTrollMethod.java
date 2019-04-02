package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.errors.InvalidValueParameterException;
import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.properties.FarmProperties;

public class FarmSpecificTrollMethod extends AbstractBotMethod {

	@Override
	public Map<String, Object> getneededParameters() {
		Map<String, Object> neededParams = new HashMap<>();
		neededParams.put(FarmProperties.TROLL_WORLD_KEY, null);
		return neededParams;
	}

	@Override
	public String getLabel() {
		return "Farm a troll. Enter NULL to define automatically.";
	}

	@Override
	public Object doProcess(BotRunner runner, Map<String, Object> params) {
		Object val = params.get(FarmProperties.TROLL_WORLD_KEY);
		runner.getBot().getGameProperties().put(FarmProperties.TROLL_WORLD_KEY, val);
		return "Now farming troll from world " + val;
	}

	@Override
	protected void verifyParameters(Map<String, Object> params) throws BotRunnerException {
		Object val = params.get(FarmProperties.TROLL_WORLD_KEY);
		if (val != null) {
			try {
				Integer.parseInt(val.toString());
			} catch (NumberFormatException e) {
				throw new InvalidValueParameterException(FarmProperties.TROLL_WORLD_KEY, val);
			}
		}
	}

}
