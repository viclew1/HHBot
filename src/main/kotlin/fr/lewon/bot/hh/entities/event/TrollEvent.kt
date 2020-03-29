package fr.lewon.bot.hh.entities.event

import com.fasterxml.jackson.annotation.JsonProperty

class TrollEvent {
    @JsonProperty("id_troll")
    var idTroll: String? = null
    @JsonProperty
    var name: String? = null
}