package fr.lewon.bot.hh.entities.input.battle

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.battle.BattleMob

class ActionBattleMob(battleMob: BattleMob) : ActionBattle(battleMob) {
    @JsonProperty("who[id_troll]")
    val whoIdTroll: String? = battleMob.idTroll
    @JsonProperty("who[id_world]")
    val whoIdWorld: String? = battleMob.idWorld

}