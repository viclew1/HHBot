package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.HHBot;
import fr.lewon.web.bot.entities.enums.Currency;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.entities.response.ChampionData;
import fr.lewon.web.bot.entities.response.TeamBattleResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

import java.util.List;
import java.util.stream.Collectors;

public class ChampionFightOperation
        extends HHOperation {
    private int championId;

    public ChampionFightOperation(int championId) {
        this.championId = championId;
    }


    @Override
    protected Delay process(BotRunner<HHBot> runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor) throws Exception {
        HHSession session = sessionManager.getSession(requestProcessor);
        String championContent = requestProcessor.getChampionPageContent(session, Integer.valueOf(this.championId));
        ChampionData championData = HtmlAnalyzer.INSTANCE.getChampionData(championContent);

        if (championData.getChampion().getCurrentTickets().intValue() == 0) {
            runner.getBotLogger().info("No ticket left, can't fight champion {}. Trying again in 4 hours", new Object[]{Integer.valueOf(this.championId)});
            return new Delay(Integer.valueOf(4), TimeScale.HOURS);
        }

        List<Integer> teamIds = championData.getTeam()
                .stream()
                .map(Girl::getId)
                .collect(Collectors.toList());

        TeamBattleResponse battleResp = requestProcessor.fightChampion(session, Currency.TICKET, this.championId, teamIds);
        if (!battleResp.getSuccess().booleanValue()) {
            runner.getBotLogger().info("Can't fight champion {}. Trying again in 4 hours", this.championId);
            return new Delay(Integer.valueOf(4), TimeScale.HOURS);
        }

        if (battleResp.getFnl().getAttackerEgo().intValue() <= 0) {
            runner.getBotLogger().info("Lost against champion {}. Trying again in 15 minutes", this.championId);
            return new Delay(Integer.valueOf(15), TimeScale.MINUTES);
        }

        runner.getBotLogger().info("Won against champion {}.", this.championId);
        return null;
    }
}
