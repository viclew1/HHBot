package fr.lewon.web.bot;

import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.AbstractBot;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.operations.InitHentaiHeroesBotOperation;

public class HHBot extends AbstractBot {

	@Override
	public List<Operation> getDefaultOperations(BotRunner runner, String login, String password) {
		return Arrays.asList(new InitHentaiHeroesBotOperation(runner, new HHSessionManager(login, password, 3600 * 12 * 1000L)));
	}

}
