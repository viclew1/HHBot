package fr.lewon.web.bot;

import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.AbstractBot;
import fr.lewon.bot.operations.AbstractOperation;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.operations.InitHentaiHeroesBotOperation;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class HHBot extends AbstractBot<HHSessionManager, HHRequestProcessor> {

	public HHBot(String login, String password, BotPropertyStore properties) {
		super(login, password, properties);
	}

	@Override
	protected List<AbstractOperation<HHSessionManager, HHRequestProcessor>> getDefaultOperations(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor) {
		return Arrays.asList(new InitHentaiHeroesBotOperation(sessionManager, requestProcessor));
	}

	@Override
	protected HHSessionManager initSessionManager(HHRequestProcessor requestProcessor, String login, String password) {
		return new HHSessionManager(requestProcessor, login, password, 3600 * 12 * 1000L);
	}

	@Override
	protected HHRequestProcessor initRequestProcessor() {
		return new HHRequestProcessor();
	}

}
