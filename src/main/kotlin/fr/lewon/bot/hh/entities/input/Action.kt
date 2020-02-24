package fr.lewon.bot.hh.entities.input

import com.fasterxml.jackson.annotation.JsonProperty

abstract class Action(clazz: String, action: String?) {

    @JsonProperty("class")
    val clazz: String = clazz

    @JsonProperty
    var action: String? = action

}