package fr.lewon.bot.hh.entities.battle

import com.fasterxml.jackson.annotation.JsonProperty

class TowerOfFameOpponentPremise {
    @JsonProperty("id_player")
    var id: String? = null
    @JsonProperty("level")
    var lvl = 0
    @JsonProperty("nb_challenges_played")
    var nbChallengesPlayed = 0

}