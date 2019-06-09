package fr.lewon.web.bot;

import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.AbstractBotRunnerBuilder;
import fr.lewon.bot.props.BotProperty;
import fr.lewon.web.bot.methods.HHBotMethods;
import fr.lewon.web.bot.properties.HHBotProperties;

public class HHBotRunnerBuilder extends AbstractBotRunnerBuilder<HHBot> {

	public HHBotRunnerBuilder() {
		super(Arrays.asList(HHBotProperties.values()), Arrays.asList(HHBotMethods.values()));
	}

	@Override
	protected HHBot initBot(String login, String password, List<BotProperty> properties) {
		return new HHBot(login, password, properties);
	}

}
