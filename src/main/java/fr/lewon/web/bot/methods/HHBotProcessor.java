package fr.lewon.web.bot.methods;

import java.util.List;

import fr.lewon.bot.methods.AbstractBotMethodProcessor;
import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public abstract class HHBotProcessor extends AbstractBotMethodProcessor<HHSessionManager, HHRequestProcessor> {

	public HHBotProcessor(String id, String label, List<BotPropertyDescriptor> defaultParamBuilders) {
		super(id, label, defaultParamBuilders);
	}

}
