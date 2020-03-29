package fr.lewon.bot.hh.entities.input.girl

import com.fasterxml.jackson.annotation.JsonProperty

class ActionGirlSingleSalary(which: Int) : ActionGirl("get_salary") {

    @JsonProperty
    val which: Int = which

}