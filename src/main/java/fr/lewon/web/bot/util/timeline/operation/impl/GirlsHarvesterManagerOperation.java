package fr.lewon.web.bot.util.timeline.operation.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.SessionManager;

public class GirlsHarvesterManagerOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(GirlsHarvesterManagerOperation.class);

	private SessionManager manager;

	public GirlsHarvesterManagerOperation(BotRunner runner, SessionManager manager) {
		super(runner);
		this.manager = manager;
	}

	private List<Integer> treatedGirls = new ArrayList<>();

	@Override
	public Integer process() throws Exception {
		SessionResponse session = manager.getSession();
		String haremContent = HHRequestProcessor.INSTANCE.getHaremContent(session);
		List<Integer> availableGirls = HtmlAnalyzer.INSTANCE.getAvailableGirlsIds(haremContent);
		availableGirls.removeAll(treatedGirls);
		for (Integer girlId : availableGirls) {
			getRunner().addAction(new HarvestGirlOperation(getRunner(), manager, girlId), 0);
		}
		treatedGirls.addAll(availableGirls);
		LOGGER.info("Harvest started on girls : {}. Trying again in 3 hours", availableGirls);
		return 10800;
	}

}
