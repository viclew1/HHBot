package fr.lewon.bot.hh.tasks

import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.TaskResult

class AutoShopTask(bot: Bot) : BotTask(bot) {

    override fun getLabel(): String {
        return "Auto shop"
    }

    override fun doExecute(bot: Bot): TaskResult {
//        val autoShopBooks = bot.botPropertyStore[HHBotProperties.AUTO_SHOP_BOOKS.descriptor] as Boolean
//        val autoShopGifts = runner.getBot().getPropStore()[HHBotProperties.AUTO_SHOP_GIFTS.descriptor] as Boolean
//        val session = sessionManager.getSession(requestProcessor)
//        val homeContent = requestProcessor.getHomeContent(session)
//        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
//        val shopContent = requestProcessor.getShopContent(session)
//        val nextShopFill = HtmlAnalyzer.INSTANCE.getNextStock(shopContent)
//        val shop = HtmlAnalyzer.INSTANCE.getShop(shopContent)
//        if (autoShopGifts) {
//            val giftsBought = buyItems(runner, requestProcessor, session, userInfos, shop.gifts)
//            runner.getBotLogger().info("Gifts bought : {}", giftsBought)
//        }
//        if (autoShopBooks) {
//            val booksBought = buyItems(runner, requestProcessor, session, userInfos, shop.books)
//            runner.getBotLogger().info("Books bought : {}", booksBought)
//        }
//        return Delay(nextShopFill!! + 5, TimeScale.SECONDS)
        return TaskResult()
    }

//    @Throws(Exception::class)
//    private fun buyItems(runner: BotRunner<*>, requestProcessor: HHRequestProcessor, session: HHSession?, userInfos: UserInfos?, items: List<Item?>?): List<String?> {
//        val boughtIds: MutableList<String?> = ArrayList()
//        for (item in items!!) {
//            userInfos.setSoftCurrency(userInfos.getSoftCurrency() - item.getPrice())
//            if (userInfos.getSoftCurrency() < 0 || !requestProcessor.buyItem(item, session).success) {
//                return boughtIds
//            }
//            boughtIds.add(item.getId())
//        }
//        return boughtIds
//    }

}