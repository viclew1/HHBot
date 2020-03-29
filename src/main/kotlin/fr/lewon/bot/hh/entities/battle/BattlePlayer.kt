package fr.lewon.bot.hh.entities.battle

import com.fasterxml.jackson.annotation.JsonProperty

class BattlePlayer : AbstractBattleOpponent() {
    @JsonProperty("id_member")
    var idMember: String? = null
    @JsonProperty("id_arena")
    var idArena: String? = null

}