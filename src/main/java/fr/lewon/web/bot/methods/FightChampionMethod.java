package fr.lewon.web.bot.methods;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.bot.props.BotPropertyType;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.HHBot;
import fr.lewon.web.bot.entities.champions.ChampionPremise;
import fr.lewon.web.bot.operations.ChampionFightOperation;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FightChampionMethod extends HHBotProcessor {

    private static final String CHAMPION_ID_KEY = "CHAMPION_ID";

    public FightChampionMethod(String id, String label) {
        super(id, label, Arrays.asList(new BotPropertyDescriptor[]{new BotPropertyDescriptor("CHAMPION_ID", BotPropertyType.INTEGER, null, "Id of the champion", true, false)}));
    }


    @Override
    protected Object doProcess(BotRunner<HHBot> runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor, BotPropertyStore params) throws BotRunnerException {
        int id = ((Integer) params.get("CHAMPION_ID")).intValue();

        try {
            HHSession session = (HHSession) sessionManager.getSession(requestProcessor);
            String championsContent = requestProcessor.getChampionsMapContent(session);
            List<ChampionPremise> championPremises = HtmlAnalyzer.INSTANCE.getChampionsIds(championsContent);


            ChampionPremise championPremise = championPremises.stream()
                    .filter(c -> (id == c.getChampionId().intValue()))
                    .findFirst()
                    .orElse(null);

            if (championPremise == null) {
                return "Invalid id, no champion found";
            }
        } catch (Exception e) {
            return "ERROR : " + e.getMessage();
        }

        runner.addAction(new ChampionFightOperation(id));
        return "Now fighting champion " + id;
    }


    @Override
    protected List<BotPropertyDescriptor> getSpecificParamBuilders(BotRunner<HHBot> runner) {
        return new ArrayList();
    }
}
