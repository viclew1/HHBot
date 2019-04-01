package fr.lewon.web.bot.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GirlsManagerOperation extends Operation {

	private HHSessionManager manager;
	private List<Girl> ownedGirls = new ArrayList<>();

	public GirlsManagerOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}


	@Override
	public Delay process() throws Exception {
		SessionResponse session = manager.getSession();
		String haremContent = HHRequestProcessor.INSTANCE.getHaremContent(session);

		List<Girl> newGirls = HtmlAnalyzer.INSTANCE.findAllGirls(haremContent).stream()
				.filter(Girl::getOwn)
				.filter(g -> !ownedGirls.contains(g))
				.collect(Collectors.toList());

		for (Girl girl : newGirls) {
			getRunner().addAction(new HarvestGirlOperation(getRunner(), manager, girl.getId()), girl.getPayIn() + 1);
			getRunner().logInfo("Harvest will start on girl {} in {} seconds", girl.getId(), girl.getPayIn() + 1);
			ownedGirls.add(girl);
		}

		return new Delay(3, TimeScale.HOURS);
	}

}
