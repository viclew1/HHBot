package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.response.UserInfos
import fr.lewon.bot.hh.entities.shop.Item
import fr.lewon.bot.hh.rest.HHRequestProcessor
import fr.lewon.bot.hh.rest.HHSession
import fr.lewon.bot.hh.rest.HtmlAnalyzer
import fr.lewon.bot.runner.Bot
import fr.lewon.bot.runner.bot.task.BotTask
import fr.lewon.bot.runner.bot.task.Delay
import fr.lewon.bot.runner.bot.task.TaskResult
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.TimeUnit

class AutoShopTask(bot: Bot) : BotTask("Auto shop", bot) {

    override fun doExecute(bot: Bot): TaskResult {
        val webClient = bot.sessionManager.getWebClient()
        val session = bot.sessionManager.getSession() as HHSession
        var requestProcessor = HHRequestProcessor()
        val homeContent = requestProcessor.getHomeContent(webClient, session)
        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        val shopContent = requestProcessor.getShopContent(webClient, session)
        val nextShopFill = HtmlAnalyzer.INSTANCE.getNextStock(shopContent)
        val shop = HtmlAnalyzer.INSTANCE.getShop(shopContent)
        if (bot.botPropertyStore.getByKey("auto_shop_books") as Boolean) {
            val giftsBought = buyItems(requestProcessor, webClient, session, userInfos, shop?.gifts ?: emptyList())
            bot.logger.info("Gifts bought : $giftsBought")
        }
        if (bot.botPropertyStore.getByKey("auto_shop_gifts") as Boolean) {
            val booksBought = buyItems(requestProcessor, webClient, session, userInfos, shop?.books ?: emptyList())
            bot.logger.info("Books bought : $booksBought")
        }
        return TaskResult(Delay(nextShopFill?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
    }

    @Throws(Exception::class)
    private fun buyItems(requestProcessor: HHRequestProcessor, webClient: WebClient, session: HHSession, userInfos: UserInfos?, items: List<Item>): List<String> {
        val boughtIds: MutableList<String> = ArrayList()
        for (item in items) {
            userInfos?.softCurrency?.minus(item.price ?: 0)?.let {
                userInfos.softCurrency = it
                if (it > 0 && requestProcessor.buyItem(webClient, session, item)?.success == true) {
                    return boughtIds
                }
            }
            item.id?.let { boughtIds.add(it) }
        }
        return boughtIds
    }

}