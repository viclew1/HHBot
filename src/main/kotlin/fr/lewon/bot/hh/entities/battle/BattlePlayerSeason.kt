package fr.lewon.bot.hh.entities.battle

import com.fasterxml.jackson.annotation.JsonProperty

class BattlePlayerSeason : AbstractBattleOpponent() {
    @JsonProperty("id_member")
    var idMember: String? = null
    @JsonProperty("id_season_arena")
    var idSeasonArena: String? = null

}