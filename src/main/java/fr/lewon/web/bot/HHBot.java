package fr.lewon.web.bot;

import fr.lewon.bot.bots.web.AbstractWebBot;
import fr.lewon.bot.operations.AbstractOperation;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.web.bot.operations.InitHentaiHeroesBotOperation;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class HHBot extends AbstractWebBot<HHBot, HHRequestProcessor, HHSessionManager> {

    public HHBot(String login, String password, BotPropertyStore properties) {
        super(login, password, properties);
    }

    @Override
    protected HHSessionManager initSessionManager(String login, String password) {
        return new HHSessionManager(login, password, 3600 * 5 * 1000L);
    }

    @Override
    protected HHRequestProcessor initRequestProcessor() {
        return new HHRequestProcessor();
    }

    @Override
    public AbstractOperation<HHBot> getInitialOperation() {
        return new InitHentaiHeroesBotOperation();
    }
}
