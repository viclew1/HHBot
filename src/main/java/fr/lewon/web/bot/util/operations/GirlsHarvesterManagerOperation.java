package fr.lewon.web.bot.util.operations;

import java.util.ArrayList;
import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GirlsHarvesterManagerOperation extends Operation {

	private HHSessionManager manager;

	public GirlsHarvesterManagerOperation(BotRunner runner, HHSessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	private List<Integer> treatedGirls = new ArrayList<>();

	@Override
	public Delay process() throws Exception {
		SessionResponse session = manager.getSession();
		String haremContent = HHRequestProcessor.INSTANCE.getHaremContent(session);
		List<Integer> availableGirls = HtmlAnalyzer.INSTANCE.getAvailableGirlsIds(haremContent);
		availableGirls.removeAll(treatedGirls);
		for (Integer girlId : availableGirls) {
			getRunner().addAction(new HarvestGirlOperation(getRunner(), manager, girlId), 0);
		}
		treatedGirls.addAll(availableGirls);
		getRunner().logInfo("Harvest started on girls : {}. Trying again in 3 hours", availableGirls);
		return new Delay(3, TimeScale.HOURS);
	}

}
