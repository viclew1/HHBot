package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.entities.output.SalaryResponse;
import fr.lewon.web.bot.util.RequestProcessor;
import fr.lewon.web.bot.util.SessionManager;
import fr.lewon.web.bot.util.timeline.operation.Operation;

public class HarvestGirlOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(HarvestGirlOperation.class);

	private int girlId;

	public HarvestGirlOperation(int girlId) {
		this.girlId = girlId;
	}

	@Override
	public Integer process() throws Exception {
		SalaryResponse sr = RequestProcessor.INSTANCE.getSalary(SessionManager.INSTANCE.getSession(), girlId);
		if (sr.getSuccess()) {
			int nextHarvestTime = sr.getTime();
			LOGGER.info("Girl {} collected. Money made : {}. Next harvest in {} seconds.", girlId, sr.getMoney(), nextHarvestTime);
			return nextHarvestTime + 5;
		}
		LOGGER.info("Girl {} can't be collected. Trying again in 5 minutes", girlId);
		return 300;
	}

}
