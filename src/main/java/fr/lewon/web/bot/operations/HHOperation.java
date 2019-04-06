package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public abstract class HHOperation extends Operation<HHSessionManager, HHRequestProcessor>{

	public HHOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

}
