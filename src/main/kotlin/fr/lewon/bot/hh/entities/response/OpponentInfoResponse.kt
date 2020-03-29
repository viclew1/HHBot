package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty

class OpponentInfoResponse : Response() {
    @JsonProperty
    var html: String? = null

}