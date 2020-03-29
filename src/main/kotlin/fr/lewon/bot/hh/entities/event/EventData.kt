package fr.lewon.bot.hh.entities.event

import com.fasterxml.jackson.annotation.JsonProperty

class EventData {

    @JsonProperty("event_name")
    var eventName: String? = null
    @JsonProperty("seconds_until_event_end")
    var secondsUntilEventEnd: Long? = null
    @JsonProperty
    var girls: List<GirlEvent>? = null
}