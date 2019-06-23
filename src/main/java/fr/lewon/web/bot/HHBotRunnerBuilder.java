package fr.lewon.web.bot;

import fr.lewon.bot.AbstractBotRunnerBuilder;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.web.bot.methods.HHBotMethods;
import fr.lewon.web.bot.properties.HHBotProperties;

public class HHBotRunnerBuilder extends AbstractBotRunnerBuilder<HHBot> {

	public HHBotRunnerBuilder() {
		super(HHBotProperties.class, HHBotMethods.class);
	}

	@Override
	protected HHBot initBot(String login, String password, BotPropertyStore properties) {
		return new HHBot(login, password, properties);
	}

}
