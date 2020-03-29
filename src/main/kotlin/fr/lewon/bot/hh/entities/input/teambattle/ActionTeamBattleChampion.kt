package fr.lewon.bot.hh.entities.input.teambattle

import fr.lewon.bot.hh.entities.enums.Currency

class ActionTeamBattleChampion(currency: Currency, defenderId: Int, attackerTeam: List<Int>) : ActionTeamBattle(null, "champion", currency, defenderId, attackerTeam) {
}