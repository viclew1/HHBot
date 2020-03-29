package fr.lewon.bot.hh.entities.champions

import com.fasterxml.jackson.annotation.JsonProperty

class Champion {
    @JsonProperty
    var id: Int? = null
    @JsonProperty
    var name: String? = null
    @JsonProperty
    var currentTickets: Int? = null

}