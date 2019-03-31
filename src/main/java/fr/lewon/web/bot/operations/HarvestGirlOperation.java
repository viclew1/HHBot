package fr.lewon.web.bot.operations;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.Operation;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SalaryResponse;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class HarvestGirlOperation extends Operation {

	private int girlId;
	private HHSessionManager manager;

	public HarvestGirlOperation(BotRunner runner, HHSessionManager manager, int girlId) {
		super(runner);
		this.manager = manager;
		this.girlId = girlId;
	}

	@Override
	public Delay process() throws Exception {
		SalaryResponse sr = HHRequestProcessor.INSTANCE.getSalary(manager.getSession(), girlId);
		if (sr.getSuccess()) {
			int nextHarvestTime = sr.getTime();
			getRunner().logInfo("Girl {} collected. Money made : {}. Next harvest in {} seconds.", girlId, sr.getMoney(), nextHarvestTime);
			return new Delay(nextHarvestTime + 5);
		}
		getRunner().logInfo("Girl {} can't be collected. Trying again in 20 minutes", girlId);
		return new Delay(20, TimeScale.MINUTES);
	}

}
