package fr.lewon.web.bot.methods;

import fr.lewon.bot.methods.IBotMethod;
import fr.lewon.bot.methods.IBotMethodProcessor;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public enum HHBotMethods implements IBotMethod<HHSessionManager, HHRequestProcessor> {

	GET_USER_INFOS("Get user infos", new GetUserInfosMethod());

	private final String label;
	private final HHBotProcessor botMethod;
	
	private HHBotMethods(String label, HHBotProcessor botMethod) {
		this.label = label;
		this.botMethod = botMethod;
	}

	@Override
	public String getId() {
		return name();
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public IBotMethodProcessor<HHSessionManager, HHRequestProcessor> getProcessor() {
		return botMethod;
	}

}
