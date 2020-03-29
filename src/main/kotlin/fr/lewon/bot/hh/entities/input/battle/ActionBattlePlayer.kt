package fr.lewon.bot.hh.entities.input.battle

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.battle.BattlePlayer

class ActionBattlePlayer(battlePlayer: BattlePlayer) : ActionBattle(battlePlayer) {
    @JsonProperty("who[id_member]")
    val whoIdMember: String? = battlePlayer.idMember
    @JsonProperty("who[id_arena]")
    val whoIdArena: String? = battlePlayer.idArena

}