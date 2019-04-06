package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SalaryResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class HarvestGirlOperation extends HHOperation {

	private int girlId;

	public HarvestGirlOperation(HHSessionManager manager, HHRequestProcessor requestProcessor, int girlId) {
		super(manager, requestProcessor);
		this.girlId = girlId;
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SalaryResponse sr = requestProcessor.getSalary(sessionManager.getSession(), girlId);
		if (sr.getSuccess()) {
			int nextHarvestTime = sr.getTime();
			runner.logInfo("Girl {} collected. Money made : {}. Next harvest in {} seconds.", girlId, sr.getMoney(), nextHarvestTime);
			return new Delay(nextHarvestTime + 1);
		}
		runner.logInfo("Girl {} can't be collected. Trying again in 20 minutes", girlId);
		return new Delay(20, TimeScale.MINUTES);
	}

}
