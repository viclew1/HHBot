package fr.lewon.bot.hh.entities.champions

import com.fasterxml.jackson.annotation.JsonProperty

class Winner {
    @JsonProperty
    var type: String? = null
    @JsonProperty
    var id: Int? = null

}