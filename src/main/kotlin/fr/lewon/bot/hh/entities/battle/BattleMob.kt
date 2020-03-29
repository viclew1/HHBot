package fr.lewon.bot.hh.entities.battle

import com.fasterxml.jackson.annotation.JsonProperty

class BattleMob : AbstractBattleOpponent() {
    @JsonProperty("id_troll")
    var idTroll: String? = null
    @JsonProperty("id_world")
    var idWorld: String? = null

}