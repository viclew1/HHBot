package fr.lewon.web.bot.entities.input.teambattle;

import java.util.Arrays;
import java.util.List;

import fr.lewon.web.bot.entities.enums.Currency;

public class ActionTeamBattleChampion extends ActionTeamBattle {
	
	public ActionTeamBattleChampion(Currency currency, Integer defenderId, Integer... attackerTeam) {
		this(currency, defenderId, Arrays.asList(attackerTeam));
	}
	
	public ActionTeamBattleChampion(Currency currency, Integer defenderId, List<Integer> attackerTeam) {
		super(null, "champion", currency, defenderId, attackerTeam);
	}

}
