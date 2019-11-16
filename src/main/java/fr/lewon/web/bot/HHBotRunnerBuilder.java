package fr.lewon.web.bot;

import fr.lewon.bot.AbstractBotRunnerBuilder;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.web.bot.methods.HHBotMethods;
import fr.lewon.web.bot.properties.HHBotProperties;

public class HHBotRunnerBuilder extends AbstractBotRunnerBuilder<HHBot> {

    public HHBotRunnerBuilder() {
        super(HHBotProperties.values(), HHBotMethods.values());
    }

    @Override
    protected HHBot buildBot(String login, String password, BotPropertyStore properties) {
        return new HHBot(login, password, properties);
    }

}
