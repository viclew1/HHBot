package fr.lewon.web.bot;

import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.AbstractBot;
import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.methods.FarmSpecificTrollMethod;
import fr.lewon.web.bot.methods.GetBotPropertiesMethod;
import fr.lewon.web.bot.methods.GetUserInfosMethod;
import fr.lewon.web.bot.operations.InitHentaiHeroesBotOperation;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class HHBot extends AbstractBot<HHSessionManager, HHRequestProcessor> {

	public HHBot() {
		super(new HHRequestProcessor());
	}

	@Override
	protected List<AbstractBotMethod<?, ?>> initBotMethods(HHSessionManager sessionManager, HHRequestProcessor requestProcessor) {
		return Arrays.asList(
				new FarmSpecificTrollMethod(sessionManager, requestProcessor),
				new GetUserInfosMethod(sessionManager, requestProcessor),
				new GetBotPropertiesMethod(sessionManager, requestProcessor));
	}

	@Override
	protected List<Operation<?, ?>> initDefaultOperations(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor) {
		return Arrays.asList(new InitHentaiHeroesBotOperation(sessionManager, requestProcessor));
	}

	@Override
	protected HHSessionManager initSessionManager(HHRequestProcessor requestProcessor, String login, String password) {
		return new HHSessionManager(requestProcessor, login, password, 3600 * 12 * 1000L);
	}

}
