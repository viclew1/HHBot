package fr.lewon.bot.hh.entities.input.leagues

import com.fasterxml.jackson.annotation.JsonProperty

class ActionLeaguesGetOpponentInfo(opponentId: String) : ActionLeagues("get_opponent_info") {

    @JsonProperty("opponent_id")
    val opponentId: String = opponentId

}