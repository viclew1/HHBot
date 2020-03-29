package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty

class SalaryResponse : Response() {
    @JsonProperty
    var time: Int? = null
    @JsonProperty
    var money: Int? = null

}