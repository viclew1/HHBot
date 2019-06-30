package fr.lewon.web.bot.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.BotPropertyStore;
import fr.lewon.bot.props.BotPropertyType;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;

public class UpgradeStatMethod extends HHBotProcessor {

	private static final String UPGRADE_COUNT_KEY = "upgrade_count";
	private static final String STAT_TO_UPGRADE_KEY = "stat_to_upgrade";
	
	public UpgradeStatMethod(String id, String label) {
		super(id, label, Arrays.asList(
				new BotPropertyDescriptor(UPGRADE_COUNT_KEY, BotPropertyType.INTEGER, 1, "Number of times to upgrade the property", true, false),
				new BotPropertyDescriptor(STAT_TO_UPGRADE_KEY, BotPropertyType.INTEGER, 1, "Stat to upgrade", true, false)
				));
	}

	@Override
	protected List<BotPropertyDescriptor> getSpecificParamBuilders(BotRunner runner) {
		return new ArrayList<>();
	}

	@Override
	protected Object doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor,
			BotPropertyStore params) throws BotRunnerException {

		Integer maxUpgradeCount = (Integer) params.get(UPGRADE_COUNT_KEY);
		Integer statToUpgrade = (Integer) params.get(STAT_TO_UPGRADE_KEY);
		int cpt = 0;
		try {
			while (cpt < maxUpgradeCount && requestProcessor.upgradeStat(sessionManager.getSession(), statToUpgrade).getSuccess()) {
				cpt++;
			}
		} catch (Exception e) {
			runner.getBotLogger().error("Upgrade failed", e);
		}
		return "Stat " + statToUpgrade + " upgraded " + cpt + " times";
	}
	
}
