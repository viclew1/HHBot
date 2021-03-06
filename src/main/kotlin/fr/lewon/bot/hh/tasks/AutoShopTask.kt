package fr.lewon.bot.hh.tasks

import fr.lewon.bot.hh.entities.response.HeroInfos
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

    override fun doExecute(): TaskResult {
        val sessionHolder = bot.sessionManager.buildSessionHolder()
        val session = sessionHolder.sessionObject as HHSession
        val webClient = sessionHolder.webClient
        val requestProcessor = session.requestProcessor

        val homeContent = requestProcessor.getHomeContent(webClient)
        val userInfos = HtmlAnalyzer.INSTANCE.getPlayerInfos(homeContent)
        val shopContent = requestProcessor.getShopContent(webClient)
        val nextShopFill = HtmlAnalyzer.INSTANCE.getNextStock(shopContent)
        val shop = HtmlAnalyzer.INSTANCE.getShop(shopContent)
        if (bot.botPropertyStore.getByKey("auto_shop_gifts") as Boolean) {
            val giftsBought = buyItems(requestProcessor, webClient, userInfos, shop?.gifts ?: emptyList())
            logger.info("Gifts bought : $giftsBought")
        }
        if (bot.botPropertyStore.getByKey("auto_shop_books") as Boolean) {
            val booksBought = buyItems(requestProcessor, webClient, userInfos, shop?.books ?: emptyList())
            logger.info("Books bought : $booksBought")
        }
        logger.info("Auto shop task done. Trying again in ${nextShopFill?.plus(5)?.toLong() ?: -1} seconds")
        return TaskResult(Delay(nextShopFill?.plus(5)?.toLong() ?: -1, TimeUnit.SECONDS))
    }

    @Throws(Exception::class)
    private fun buyItems(requestProcessor: HHRequestProcessor, webClient: WebClient, userInfos: HeroInfos?, items: List<Item>): List<String> {
        val boughtIds: MutableList<String> = ArrayList()
        for (item in items) {
            userInfos?.softCurrency?.minus(item.price ?: 0)?.let {
                userInfos.softCurrency = it
                if (it < 0 || requestProcessor.buyItem(webClient, item)?.success == false) {
                    return boughtIds
                }
                item.id?.let { id -> boughtIds.add(id) }
            }
        }
        return boughtIds
    }

}