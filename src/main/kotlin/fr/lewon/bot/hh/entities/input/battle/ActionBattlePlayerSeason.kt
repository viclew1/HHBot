package fr.lewon.bot.hh.entities.input.battle

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.battle.BattlePlayerSeason

class ActionBattlePlayerSeason(battlePlayerSeason: BattlePlayerSeason) : ActionBattle(battlePlayerSeason) {
    @JsonProperty("who[id_member]")
    val whoIdMember: String? = battlePlayerSeason.idMember
    @JsonProperty("who[id_season_arena]")
    val whoIdArena: String? = battlePlayerSeason.idSeasonArena

}