package fr.lewon.web.bot.methods;

import fr.lewon.bot.bots.web.AbstractWebBotMethodProcessor;
import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.web.bot.HHBot;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

import java.util.List;

public abstract class HHBotProcessor extends AbstractWebBotMethodProcessor<HHBot, HHRequestProcessor, HHSessionManager> {

    public HHBotProcessor(String id, String label, List<BotPropertyDescriptor> defaultParamBuilders) {
        super(id, label, defaultParamBuilders);
    }

}
