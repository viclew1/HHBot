package fr.lewon.bot.hh.entities.input.girl

import com.fasterxml.jackson.annotation.JsonProperty

class ActionGirlAllSalaries : ActionGirl("get_all_salaries") {
    @JsonProperty
    val where = "home"
    @JsonProperty
    val mc = true
}