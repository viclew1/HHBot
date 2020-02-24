package fr.lewon.bot.hh.entities.girls

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.quests.QuestPremise

class GirlQuests {
    @JsonProperty("for_upgrade")
    var forUpgrade: QuestPremise? = null

}