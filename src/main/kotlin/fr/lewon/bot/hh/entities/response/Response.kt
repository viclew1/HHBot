package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty

open class Response {
    @JsonProperty
    var success: Boolean? = null
    @JsonProperty
    var error: String? = null

}