package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

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
		return "Farm specific troll. Null to define automatically.";
	}

	@Override
	public Object process(BotRunner runner, Map<String, Object> params) {
		Object val = params.get(FarmProperties.TROLL_WORLD_KEY);
		if (val != null && !(val instanceof Integer)) {
			return "Parameter [" + FarmProperties.TROLL_WORLD_KEY + "] should be an integer";
		}

		runner.getBot().getGameProperties().put(FarmProperties.TROLL_WORLD_KEY, val);
		return "Now farming troll from world " + val;
	}

}
