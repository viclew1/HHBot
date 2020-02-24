package fr.lewon.bot.hh.entities.diverse

import com.fasterxml.jackson.annotation.JsonProperty

class Cost {
    @JsonProperty("$")
    var regularCost: Int? = null
    @JsonProperty("HC")
    var specialCost: Int? = null
    @JsonProperty("*")
    var energyCost: Int? = null

}