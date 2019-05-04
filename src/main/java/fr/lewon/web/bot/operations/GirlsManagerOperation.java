package fr.lewon.web.bot.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.SessionResponse;
import fr.lewon.web.bot.entities.UserInfos;
import fr.lewon.web.bot.entities.enums.Rarity;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.entities.quests.QuestStep;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GirlsManagerOperation extends HHOperation {

	private List<Girl> ownedGirls = new ArrayList<>();

	public GirlsManagerOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		String haremContent = requestProcessor.getHaremContent(session);

		List<Girl> newGirls = HtmlAnalyzer.INSTANCE.findAllGirls(haremContent).stream()
				.filter(Girl::getOwn)
				.filter(g -> !ownedGirls.contains(g))
				.collect(Collectors.toList());

		for (Girl girl : newGirls) {
			runner.addAction(new HarvestGirlOperation(sessionManager, requestProcessor, girl.getId()), girl.getPayIn() + 1);
			runner.logInfo("Harvest will start on girl {} in {} seconds", girl.getId(), girl.getPayIn() + 1);
			ownedGirls.add(girl);
		}

		//		while (true) {
		//			UserInfos userInfos = getUserInfos(requestProcessor, session);
		//			Girl toUpgrade = getGirlToUpgrade();
		//			if (toUpgrade != null) {
		//				if (upgradeGirl(requestProcessor, session, toUpgrade, userInfos.getSoftCurrency())) {
		//					toUpgrade.setCanUpgrade(false);
		//					runner.logInfo("Girl {} upgraded", toUpgrade.getId());
		//				} else {
		//					runner.logInfo("Not enough money to upgrade girl {}.", toUpgrade.getId());
		//					break;
		//				}
		//			} else {
		//				break;
		//			}
		//		}


		return new Delay(3, TimeScale.HOURS);
	}



	private UserInfos getUserInfos(HHRequestProcessor requestProcessor, SessionResponse session) throws ServerException, IOException {
		String homeContent = requestProcessor.getHomeContent(session);
		return HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
	}

	private boolean upgradeGirl(HHRequestProcessor requestProcessor, SessionResponse session, Girl girl, Integer currentMoney) throws ServerException, IOException {
		Long idQuest = girl.getQuests().getForUpgrade().getIdQuest();
		String girlQuestContent = requestProcessor.getQuestContent(session, idQuest);
		QuestStep[] steps = HtmlAnalyzer.INSTANCE.getQuestSteps(girlQuestContent);
		QuestStep step = steps[steps.length - 1];
		if (step.getCost().getRegularCost() > currentMoney) {
			return false;
		}
		return requestProcessor.continueQuest(session, idQuest).getSuccess();
	}


	private void feedGirl(HHRequestProcessor requestProcessor, SessionResponse session, Girl girl) {
	}

	private Girl getGirlToUpgrade() {
		return ownedGirls.stream()
				.filter(g -> g.isCanUpgrade())
				.sorted(new Comparator<Girl>() {
					@Override
					public int compare(Girl g1, Girl g2) {
						Rarity r1 = Rarity.getValueByLabel(g1.getRarity());
						Rarity r2 = Rarity.getValueByLabel(g2.getRarity());
						if (r1 != r2) {
							return r1.getValue().compareTo(r2.getValue());
						}
						return g1.getAffection().getLevel().compareTo(g2.getAffection().getLevel());
					}
				}).findFirst().orElse(null);
	}

	private Girl getGirlToFeed() {
		return ownedGirls.stream()
				.filter(g -> !g.getAffection().getMaxed())
				.sorted(new Comparator<Girl>() {
					@Override
					public int compare(Girl g1, Girl g2) {
						return g1.getAffection().getLeft().compareTo(g2.getAffection().getLeft());
					}
				}).findFirst().orElse(null);
	}

}
