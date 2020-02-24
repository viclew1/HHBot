package fr.lewon.bot.hh.entities.input.teambattle

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.enums.Currency
import fr.lewon.bot.hh.entities.input.Action

abstract class ActionTeamBattle(action: String?, battleType: String, currency: Currency, defenderId: Int, attackerTeam: List<Int>) : Action("TeamBattle", action) {
    @JsonProperty("battle_type")
    val battleType: String = battleType
    @JsonProperty
    val currency: String = currency.strValue
    @JsonProperty("defender_id")
    val defenderId: Int = defenderId
    @JsonProperty("attacker[team][]")
    val attackerTeam: List<Int> = attackerTeam
    @JsonProperty("battles_amount")
    val battlesAmount: Int = 1
}