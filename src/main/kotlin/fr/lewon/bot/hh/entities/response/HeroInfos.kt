package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.quests.QuestPremise

class HeroInfos {
    @JsonProperty("soft_currency")
    var softCurrency: Int? = null
    @JsonProperty("hard_currency")
    var hardCurrency: Int? = null
    @JsonProperty
    var questing: QuestPremise? = null
}