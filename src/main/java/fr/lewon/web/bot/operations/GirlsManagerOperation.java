package fr.lewon.web.bot.operations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.enums.Rarity;
import fr.lewon.web.bot.entities.girls.Girl;
import fr.lewon.web.bot.entities.quests.QuestStep;
import fr.lewon.web.bot.entities.response.SessionResponse;
import fr.lewon.web.bot.entities.response.UserInfos;
import fr.lewon.web.bot.properties.HHBotProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class GirlsManagerOperation extends HHOperation {

	private List<Integer> ownedGirlsIds = new ArrayList<>();

	public GirlsManagerOperation(HHSessionManager manager, HHRequestProcessor requestProcessor) {
		super(manager, requestProcessor);
	}

	@Override
	public Delay doProcess(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		SessionResponse session = sessionManager.getSession();
		String haremContent = requestProcessor.getHaremContent(session);

		List<Girl> ownedGirls = HtmlAnalyzer.INSTANCE.findAllGirls(haremContent).stream()
				.filter(Girl::getOwn)
				.collect(Collectors.toList());
		
		List<Girl> newGirls = ownedGirls.stream()
				.filter(g -> !ownedGirlsIds.contains(g.getId()))
				.collect(Collectors.toList());

		for (Girl girl : newGirls) {
			runner.addAction(new HarvestGirlOperation(sessionManager, requestProcessor, girl.getId()), girl.getPayIn() + 1);
			runner.getBotLogger().info("Harvest will start on girl {} in {} seconds", girl.getId(), girl.getPayIn() + 1);
			ownedGirlsIds.add(girl.getId());
		}

		if ((boolean) runner.getBot().getPropStore().get(HHBotProperties.AUTO_FEED_GIRLS.getDescriptor())) {
			autoFeedGirls(runner, requestProcessor, session, ownedGirls);
		}
		
		if ((boolean) runner.getBot().getPropStore().get(HHBotProperties.AUTO_UPGRADE_GIRLS.getDescriptor())) {
			autoUpgradeGirls(runner, requestProcessor, session, ownedGirls);
		}

		return new Delay(3, TimeScale.HOURS);
	}

	private UserInfos getUserInfos(HHRequestProcessor requestProcessor, SessionResponse session) throws Exception {
		String homeContent = requestProcessor.getHomeContent(session);
		return HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
	}

	private void autoUpgradeGirls(BotRunner runner, HHRequestProcessor requestProcessor, SessionResponse session, List<Girl> girls) throws Exception {
		List<Girl> girlsToUpgrade = getGirlsToUpgrade(girls);
		for (Girl toUpgrade : girlsToUpgrade) {
			UserInfos userInfos = getUserInfos(requestProcessor, session);
			if (upgradeGirl(requestProcessor, session, toUpgrade, userInfos.getSoftCurrency())) {
				runner.getBotLogger().info("Girl {} upgraded", toUpgrade.getId());
			} else {
				runner.getBotLogger().info("Couldn't upgrade girl {}.", toUpgrade.getId());
				break;
			}
		}
	}

	private boolean upgradeGirl(HHRequestProcessor requestProcessor, SessionResponse session, Girl girl, Integer currentMoney) throws Exception {
		Long idQuest = girl.getQuests().getForUpgrade().getIdQuest();
		String girlQuestContent = requestProcessor.getQuestContent(session, idQuest);
		QuestStep[] steps = HtmlAnalyzer.INSTANCE.getQuestSteps(girlQuestContent);
		QuestStep step = steps[steps.length - 1];
		if (step.getCost().getRegularCost() > currentMoney) {
			return false;
		}
		return requestProcessor.continueQuest(session, idQuest).getSuccess();
	}

	private List<Girl> getGirlsToUpgrade(List<Girl> girls) {
		return girls.stream()
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
				}).collect(Collectors.toList());
	}
	
	private void autoFeedGirls(BotRunner runner, HHRequestProcessor requestProcessor, SessionResponse session, List<Girl> girls) throws Exception {
		List<Girl> girlsToFeed = getGirlsToFeed(girls);
		for (Girl toFeed : girlsToFeed) {
			if (feedGirl(requestProcessor, session, toFeed)) {
				runner.getBotLogger().info("Girl {} fed", toFeed.getId());
			} else {
				runner.getBotLogger().info("Couldn't feed girl {}.", toFeed.getId());
				break;
			}
		}
	}

	private List<Girl> getGirlsToFeed(List<Girl> girls) {
		return girls.stream()
				.filter(g -> !g.getAffection().getMaxed())
				.sorted(new Comparator<Girl>() {
					@Override
					public int compare(Girl g1, Girl g2) {
						return g1.getAffection().getLeft().compareTo(g2.getAffection().getLeft());
					}
				}).collect(Collectors.toList());
	}
	
	private boolean feedGirl(HHRequestProcessor requestProcessor, SessionResponse session, Girl girl) {
		
		return false;
	}

}
