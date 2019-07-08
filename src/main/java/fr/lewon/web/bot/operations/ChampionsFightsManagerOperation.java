package fr.lewon.web.bot.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.champions.ChampionPremise;
import fr.lewon.web.bot.entities.response.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class ChampionsFightsManagerOperation extends HHOperation {

	private List<Integer> managedChampionsIds;
	
	public ChampionsFightsManagerOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
		this.managedChampionsIds = new ArrayList<>();
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {
		
		SessionResponse session = sessionManager.getSession();
		String championsContent = requestProcessor.getChampionsMapContent(session);
		List<ChampionPremise> championPremises = HtmlAnalyzer.INSTANCE.getChampionsIds(championsContent).stream()
				.filter(c -> !managedChampionsIds.contains(c.getChampionId()))
				.collect(Collectors.toList());
		
		for (ChampionPremise premise : championPremises) {
			Integer id = premise.getChampionId();
			Integer waitTime = premise.getSecondsToWait();
			runner.addAction(new ChampionFightOperation(sessionManager, requestProcessor, id), waitTime);
			managedChampionsIds.add(id);
			runner.getBotLogger().info("Farming champion {} in {} seconds", id, waitTime);
		}
		
		runner.getBotLogger().info("Finding new champions to farm in 1 day");
		return new Delay(1, TimeScale.DAYS);
	}

}
