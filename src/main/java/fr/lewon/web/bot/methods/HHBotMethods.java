package fr.lewon.web.bot.methods;

import fr.lewon.bot.methods.AbstractBotMethodProcessor;
import fr.lewon.bot.methods.IBotMethodEnum;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public enum HHBotMethods implements IBotMethodEnum<HHSessionManager, HHRequestProcessor> {

	GET_USER_INFOS(new GetUserInfosMethod("GET_USER_INFOS", "Get user infos"));

	private final HHBotProcessor botMethod;
	
	private HHBotMethods(HHBotProcessor botMethod) {
		this.botMethod = botMethod;
	}

	@Override
	public AbstractBotMethodProcessor<HHSessionManager, HHRequestProcessor> getProcessor() {
		return botMethod;
	}

}
