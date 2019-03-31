package fr.lewon.web.bot.methods;

import java.util.HashMap;
import java.util.Map;

import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.bot.runner.BotRunner;

public class GetUserInfosMethod extends AbstractBotMethod {

	@Override
	public Map<String, Object> getneededParameters() {
		return new HashMap<>();
	}

	@Override
	public String getLabel() {
		return "Get user infos";
	}

	@Override
	public Object process(BotRunner runner, Map<String, Object> params) {
		return null;
	}

}
