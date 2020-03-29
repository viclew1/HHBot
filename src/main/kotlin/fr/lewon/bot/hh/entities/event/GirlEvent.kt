package fr.lewon.bot.hh.entities.event

import com.fasterxml.jackson.annotation.JsonProperty

class GirlEvent {
    @JsonProperty
    var name: String? = null
    @JsonProperty
    var shards: Int? = null
    @JsonProperty
    var troll: TrollEvent? = null

}