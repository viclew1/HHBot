package fr.lewon.bot.hh.entities.girls

import com.fasterxml.jackson.annotation.JsonProperty

class AffectionInfos {
    @JsonProperty
    var cur: Int? = null
    @JsonProperty
    var min: Int? = null
    @JsonProperty
    var max: Int? = null
    @JsonProperty
    var level: Int? = null
    @JsonProperty
    var left: Int? = null
    @JsonProperty
    var maxed: Boolean? = null

}