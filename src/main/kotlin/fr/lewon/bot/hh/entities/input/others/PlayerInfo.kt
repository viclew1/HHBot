package fr.lewon.bot.hh.entities.input.others

import com.fasterxml.jackson.annotation.JsonProperty


class PlayerInfo(login: String, password: String) {
    @JsonProperty
    val login: String = login
    @JsonProperty
    val password: String = password
    @JsonProperty("stay_online")
    val stayOnline = "1"
    @JsonProperty("module")
    val module = "Member"
    @JsonProperty("action")
    val action = "form_log_in"
    @JsonProperty("call")
    val call = "Member"

}