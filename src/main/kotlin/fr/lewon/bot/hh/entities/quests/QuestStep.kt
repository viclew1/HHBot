package fr.lewon.bot.hh.entities.quests

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.diverse.Cost

class QuestStep {
    @JsonProperty
    var cost: Cost? = null

}