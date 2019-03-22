package fr.lewon.web.bot.util.timeline.operation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Operation;
import fr.lewon.web.bot.entities.output.SalaryResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.SessionManager;

public class HarvestGirlOperation extends Operation {

	private static final Logger LOGGER = LoggerFactory.getLogger(HarvestGirlOperation.class);

	private int girlId;
	private SessionManager manager;

	public HarvestGirlOperation(BotRunner runner, SessionManager manager, int girlId) {
		super(runner);
		this.manager = manager;
		this.girlId = girlId;
	}

	@Override
	public Integer process() throws Exception {
		SalaryResponse sr = HHRequestProcessor.INSTANCE.getSalary(manager.getSession(), girlId);
		if (sr.getSuccess()) {
			int nextHarvestTime = sr.getTime();
			LOGGER.info("Girl {} collected. Money made : {}. Next harvest in {} seconds.", girlId, sr.getMoney(), nextHarvestTime);
			return nextHarvestTime + 5;
		}
		LOGGER.info("Girl {} can't be collected. Trying again in 5 minutes", girlId);
		return 300;
	}

}
