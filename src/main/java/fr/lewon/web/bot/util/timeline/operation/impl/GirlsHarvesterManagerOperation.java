package fr.lewon.web.bot.util.timeline.operation.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.util.HtmlAnalyzer;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.TimeLineManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class GirlsHarvesterManagerOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(GirlsHarvesterManagerOperation.class);

	private List<Integer> treatedGirls = new ArrayList<>();

	@Override
	public Integer process() throws Exception {
		SessionResponse session = SessionManager.INSTANCE.getSession();
		String haremContent = RequestProcessor.INSTANCE.getHaremContent(session);
		List<Integer> availableGirls = HtmlAnalyzer.INSTANCE.getAvailableGirlsIds(haremContent);
		availableGirls.removeAll(treatedGirls);
		for (Integer girlId : availableGirls) {
			TimeLineManager.INSTANCE.addAction(new HarvestGirlOperation(girlId), 0);
		}
		treatedGirls.addAll(availableGirls);
		LOGGER.info("Harvest started on girls : {}. Trying again in 3 hours", availableGirls);
		return 10800;
	}

}
