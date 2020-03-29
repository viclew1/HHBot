package fr.lewon.bot.hh.entities.champions

import com.fasterxml.jackson.annotation.JsonProperty

class Final {
    @JsonProperty("defender_impression")
    var defenderImpression: Int? = null
    @JsonProperty("attacker_ego")
    var attackerEgo: Int? = null
    @JsonProperty
    var winner: Winner? = null

}