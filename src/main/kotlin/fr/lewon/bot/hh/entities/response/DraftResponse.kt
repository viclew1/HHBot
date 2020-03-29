package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.girls.Girl

class DraftResponse : Response() {
    @JsonProperty("teamArray")
    var team: List<Girl>? = null
    @JsonProperty
    var canDraft: Boolean? = null
    @JsonProperty
    var freeDrafts: Int? = null
    @JsonProperty
    var priceDraft: Int? = null

}