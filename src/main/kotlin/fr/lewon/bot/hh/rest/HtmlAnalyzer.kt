package fr.lewon.bot.hh.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.lewon.bot.hh.entities.activities.Mission
import fr.lewon.bot.hh.entities.battle.BattleMob
import fr.lewon.bot.hh.entities.battle.BattlePlayer
import fr.lewon.bot.hh.entities.battle.TowerOfFameOpponentPremise
import fr.lewon.bot.hh.entities.champions.ChampionPremise
import fr.lewon.bot.hh.entities.girls.Girl
import fr.lewon.bot.hh.entities.quests.QuestStep
import fr.lewon.bot.hh.entities.response.ChampionData
import fr.lewon.bot.hh.entities.response.UserInfos
import fr.lewon.bot.hh.entities.shop.Item
import fr.lewon.bot.hh.entities.shop.Shop
import java.io.IOException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

enum class HtmlAnalyzer {
    INSTANCE;

    private val objectMapper = ObjectMapper()

    @Throws(IOException::class)
    fun getCompetitions(activityPage: String?): List<Int> {
        val regex = "<div class=\"contest\" id_contest=\"([0-9]+)\">.*?<div class=\"(.*?)\""
        val matcher = matchPattern(activityPage, regex)
        val competitions: MutableList<Int> = ArrayList()
        while (matcher.find()) {
            val divEndedClass = matcher.group(2)
            if ("contest_header ended" == divEndedClass) {
                competitions.add(matcher.group(1).toInt())
            }
        }
        return competitions
    }

    @Throws(IOException::class)
    fun getSecondsUntilNextCompetition(activityPage: String?): Int {
        val regex = "data-remaining_time=\"([0-9]+)\""
        val matcher = matchPattern(activityPage, regex)
        var minTimeSeconds: Int? = null
        while (matcher.find()) {
            val waitTimeSeconds = matcher.group(1).toInt()
            if (minTimeSeconds == null || minTimeSeconds > waitTimeSeconds) {
                minTimeSeconds = waitTimeSeconds
            }
        }
        return minTimeSeconds ?: 24 * 60 * 60
    }

    @Throws(IOException::class)
    fun getChampionData(championContent: String?): ChampionData? {
        val regex = "var championData = (\\{.*?});"
        val matcher = matchPattern(championContent, regex)
        return if (matcher.find()) {
            objectMapper.readValue<ChampionData>(matcher.group(1))
        } else null
    }

    fun getChampionsIds(championsMapContent: String?): List<ChampionPremise> {
        val regex = ("<a href=\"champions/([0-9]+)\" class=\"champion-lair\""
                + ".*?"
                + "<div class=\"champion-lair-name map-label-link\">"
                + "(.*?)"
                + "</div>")
        val matcher = matchPattern(championsMapContent, regex)
        val premises: MutableList<ChampionPremise> = ArrayList()
        while (matcher.find()) {
            val id = matcher.group(1).toInt()
            val waitTime = getWaitTime(matcher.group(2))
            premises.add(ChampionPremise(id, waitTime))
        }
        return premises
    }

    private fun getWaitTime(championLairContent: String): Int {
        val regex = "<div rel=\"timer\" timer=\"([0-9]+)\">"
        val matcher = matchPattern(championLairContent, regex)
        if (matcher.find()) {
            val timer = matcher.group(1).toLong()
            val waitTime = (timer - System.currentTimeMillis() / 1000).toInt()
            return if (waitTime < 0) 0 else waitTime
        }
        return 0
    }

    @Throws(IOException::class)
    fun getQuestSteps(questContent: String?): Array<QuestStep>? {
        val regex = "Q.steps = (\\[.*?\\]);"
        val matcher = matchPattern(questContent, regex)
        if (!matcher.find()) {
            return null
        }
        val json = matcher.group(1).replace("\"cost\":[]", "\"cost\":{}")
        return objectMapper.readValue<Array<QuestStep>>(json)
    }

    @Throws(IOException::class)
    fun findAllGirls(haremContent: String): List<Girl> {
        val girls: MutableList<Girl> = ArrayList()
        val regex = "girlsDataList\\['[0-9]+'\\] = (\\{.*?};)"
        val matcher = matchPattern(haremContent, regex)
        while (matcher.find()) {
            val girl = objectMapper.readValue<Girl>(matcher.group(1))
            girls.add(girl)
        }
        return girls
    }

    @Throws(IOException::class)
    fun getPlayerInfos(homeContent: String?): UserInfos? {
        val regex = "Hero.infos = (\\{.*?};)"
        val matcher = matchPattern(homeContent, regex)
        return if (!matcher.find()) {
            null
        } else objectMapper.readValue<UserInfos>(matcher.group(1))
    }

    @Throws(IOException::class)
    fun findOpponentBattlePlayer(content: String?): BattlePlayer? {
        val regex = "hh_battle_players =.*?\\{.*?},.*?(\\{.*?})"
        val matcher = matchPattern(content, regex)
        if (!matcher.find()) {
            return null
        }
        val battlePlayerStr = matcher.group(1)
        return objectMapper.readValue<BattlePlayer>(battlePlayerStr)
    }

    @Throws(IOException::class)
    fun findOpponentBattleMob(battleTrollContent: String?): BattleMob? {
        val regex = "hh_battle_players =.*?\\{.*?},.*?(\\{.*?})"
        val matcher = matchPattern(battleTrollContent, regex)
        if (!matcher.find()) {
            return null
        }
        val battlePlayerStr = matcher.group(1)
        return objectMapper.readValue<BattleMob>(battlePlayerStr)
    }

    fun getCurrentWorldId(mapContent: String?): String? {
        val regex = "<a class=\"link-world\" href=\"/world/([0-9])\""
        val matcher = matchPattern(mapContent, regex)
        var lastWorldId: String? = null
        while (matcher.find()) {
            lastWorldId = matcher.group(1)
        }
        return lastWorldId
    }

    fun getTrollId(worldContent: String?): String? {
        val regex = "id_troll=([0-9])"
        val matcher = matchPattern(worldContent, regex)
        return if (matcher.find()) {
            matcher.group(1)
        } else null
    }

    fun getNextStock(shopContent: String?): Int? {
        val regex = "<span rel=\"count\" time=\"([0-9]+)\">"
        val matcher = matchPattern(shopContent, regex)
        return if (matcher.find()) {
            matcher.group(1).toInt()
        } else null
    }

    @Throws(IOException::class)
    fun getShop(shopContent: String?): Shop? {
        val regex = "<div tab class=\"armor\" type=\"armor\">(.*?)<button rel=\"buy\""
        val matcher = matchPattern(shopContent, regex)
        if (matcher.find()) {
            val subShopContent = matcher.group(1)
                    .replace("&quot;", "\"")
                    .replace("data-d=", "\ndata-d=")
            val books = getBooks(subShopContent)
            val gifts = getGifts(subShopContent)
            return Shop(books, gifts)
        }
        return null
    }

    @Throws(IOException::class)
    private fun getGifts(subShopContent: String): List<Item> {
        val regex = "data-d=\"(\\{.*?\"type\":\"gift\".*?})\">"
        val matcher = matchPattern(subShopContent, regex, false)
        val gifts: MutableList<Item> = ArrayList()
        while (matcher.find()) {
            gifts.add(objectMapper.readValue(matcher.group(1)))
        }
        return gifts
    }

    @Throws(IOException::class)
    private fun getBooks(subShopContent: String): List<Item> {
        val regex = "data-d=\"(\\{.*?\"type\":\"potion\".*?})\">"
        val matcher = matchPattern(subShopContent, regex, false)
        val books: MutableList<Item> = ArrayList()
        while (matcher.find()) {
            books.add(objectMapper.readValue(matcher.group(1)))
        }
        return books
    }

    @Throws(IOException::class)
    fun findHallOfFameOpponents(content: String?): List<TowerOfFameOpponentPremise?> {
        val regex = "leagues_list\\.push\\( (\\{.*?}) \\);"
        val matcher = matchPattern(content, regex)
        val premises: MutableList<TowerOfFameOpponentPremise?> = ArrayList()
        while (matcher.find()) {
            premises.add(objectMapper.readValue<TowerOfFameOpponentPremise>(matcher.group(1)))
        }
        return premises
    }

    private fun matchPattern(content: String?, regex: String, flatten: Boolean = true): Matcher {
        var content = content
        content = if (flatten) flattenContent(content) else content
        val pattern = Pattern.compile(regex)
        return pattern.matcher(content)
    }

    private fun flattenContent(content: String?): String {
        return content!!.replace("[\r\n]+".toRegex(), " ")
    }

    @Throws(IOException::class)
    fun getMissions(activityPage: String): MutableList<Mission> {
        val missions: MutableList<Mission> = ArrayList()
        val regexMissionsWrap = "<div class=\"missions_wrap\">(.*?)<script type=\"text/javascript\""
        val matcherMissionsWrap = matchPattern(activityPage, regexMissionsWrap)
        if (!matcherMissionsWrap.find()) {
            return missions
        }
        val missionsWrapContent = matcherMissionsWrap.group(1)
        val missionBodyRegex = "<div class=\"mission_object sub_block (.*?)\" data-d='(\\{.*?})'.*?<button rel=\"mission_start\" class=\"blue_text_button\" (.*?)>"
        val matcherMissions = matchPattern(missionsWrapContent, missionBodyRegex)
        while (matcherMissions.find()) {
            val rarity = matcherMissions.group(1)
            val missionBody = matcherMissions.group(2)
            val displayStart = matcherMissions.group(3)
            val startable = displayStart == null || "" == displayStart
            val mission = objectMapper.readValue<Mission>(missionBody)
            mission.setRarity(rarity)
            mission.isStartable = startable
            missions.add(mission)
        }
        return missions
    }
}