package fr.lewon.web.bot.methods;

import fr.lewon.bot.methods.AbstractBotMethod;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public abstract class HHBotMethod extends AbstractBotMethod<HHSessionManager, HHRequestProcessor>{

	public HHBotMethod(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

}
