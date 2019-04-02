package fr.lewon.web.bot;

import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.AbstractBot;
import fr.lewon.bot.http.AbstractSessionManager;
import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.methods.FarmSpecificTrollMethod;
import fr.lewon.web.bot.methods.GetBotPropertiesMethod;
import fr.lewon.web.bot.methods.GetUserInfosMethod;
import fr.lewon.web.bot.operations.InitHentaiHeroesBotOperation;
import fr.lewon.web.bot.util.HHSessionManager;

public class HHBot extends AbstractBot {

	@Override
	protected List<AbstractBotMethod> initBotMethods(AbstractSessionManager<?> sessionManager) {
		HHSessionManager manager = (HHSessionManager) sessionManager;
		return Arrays.asList(new FarmSpecificTrollMethod(), new GetUserInfosMethod(manager), new GetBotPropertiesMethod());
	}

	@Override
	protected List<Operation> initDefaultOperations(BotRunner runner, AbstractSessionManager<?> sessionManager) {
		HHSessionManager manager = (HHSessionManager) sessionManager;
		return Arrays.asList(new InitHentaiHeroesBotOperation(manager));
	}

	@Override
	protected AbstractSessionManager<?> initSessionManager(String login, String password) {
		return new HHSessionManager(login, password, 3600 * 12 * 1000L);
	}

}
