package fr.lewon.web.bot.operations;

import java.util.ArrayList;
import java.util.List;

import fr.lewon.bot.runner.BotRunner;
import fr.lewon.bot.runner.Delay;
import fr.lewon.bot.runner.TimeScale;
import fr.lewon.web.bot.entities.response.UserInfos;
import fr.lewon.web.bot.entities.shop.Item;
import fr.lewon.web.bot.entities.shop.Shop;
import fr.lewon.web.bot.properties.HHBotProperties;
import fr.lewon.web.bot.util.HHRequestProcessor;
import fr.lewon.web.bot.util.HHSession;
import fr.lewon.web.bot.util.HHSessionManager;
import fr.lewon.web.bot.util.HtmlAnalyzer;

public class AutoShopOperation extends HHOperation {

	@Override
	public Delay process(BotRunner runner, HHSessionManager sessionManager, HHRequestProcessor requestProcessor)
			throws Exception {

		boolean autoShopBooks = (boolean) runner.getBot().getPropStore().get(HHBotProperties.AUTO_SHOP_BOOKS.getDescriptor());
		boolean autoShopGifts = (boolean) runner.getBot().getPropStore().get(HHBotProperties.AUTO_SHOP_GIFTS.getDescriptor());

		HHSession session = sessionManager.getSession(requestProcessor);
		
		String homeContent = requestProcessor.getHomeContent(session);
		UserInfos userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent);
		
		String shopContent = requestProcessor.getShopContent(session);
		
		Integer nextShopFill = HtmlAnalyzer.INSTANCE.getNextStock(shopContent);
		Shop shop = HtmlAnalyzer.INSTANCE.getShop(shopContent);

		if (autoShopGifts) {
			List<String> giftsBought = buyItems(runner, requestProcessor, session, userInfos, shop.getGifts());
			runner.getBotLogger().info("Gifts bought : {}", giftsBought);
		}
		if (autoShopBooks) {
			List<String> booksBought = buyItems(runner, requestProcessor, session, userInfos, shop.getBooks());
			runner.getBotLogger().info("Books bought : {}", booksBought);
		}
		
		return new Delay(nextShopFill + 5, TimeScale.SECONDS);
	}

	private List<String> buyItems(BotRunner runner, HHRequestProcessor requestProcessor, HHSession session, UserInfos userInfos, List<Item> items) throws Exception {
		List<String> boughtIds = new ArrayList<>();
		for (Item item : items) {
			userInfos.setSoftCurrency(userInfos.getSoftCurrency() - item.getPrice());
			if (userInfos.getSoftCurrency() < 0 || !requestProcessor.buyItem(item, session).getSuccess()) {
				return boughtIds;
			}
			boughtIds.add(item.getId());
		}
		return boughtIds;
	}

}
