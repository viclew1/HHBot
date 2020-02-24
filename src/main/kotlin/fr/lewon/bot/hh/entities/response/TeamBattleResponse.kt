package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.champions.Final

class TeamBattleResponse : Response() {
    @JsonProperty("final")
    var fnl: Final? = null

}