package fr.lewon.bot.hh.entities.input.stat

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.input.Action

class ActionUpgradeStat(carac: Int) : Action("Hero", "update_stats") {

    @JsonProperty
    val carac: Int = carac
}