package fr.lewon.web.bot.methods;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.errors.InvalidValueParameterException;
import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.bot.props.BotPropertyType;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessTrollFightsMethod extends HHBotProcessor {

    private static final String TROLL_WORLD_PARAM = "troll_world";
    private static final String FIGHTS_COUNT_PARAM = "fights_count";

    public ProcessTrollFightsMethod(String id, String label) {
        super(id, label, Arrays.asList(
                new BotPropertyDescriptor(TROLL_WORLD_PARAM, BotPropertyType.INTEGER, null, "World in which the troll will be fought", true, false),
                new BotPropertyDescriptor(FIGHTS_COUNT_PARAM, BotPropertyType.INTEGER, null, "Number of fights to process. Will fight all you can if null", true, true)));
    }

    @Override
    protected List<BotPropertyDescriptor> getSpecificParamBuilders(BotRunner runner) {
        return new ArrayList<>();
    }

    @Override
    protected Object doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor,
                               BotPropertyStore params) throws BotRunnerException {

        Object trollWorldVal = params.get(TROLL_WORLD_PARAM);
        Object fightsCountVal = params.get(FIGHTS_COUNT_PARAM);

        if (trollWorldVal == null || fightsCountVal == null) {
            throw new InvalidValueParameterException(TROLL_WORLD_PARAM, trollWorldVal);
        }

        return null;
    }

}
