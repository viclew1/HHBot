package fr.lewon.web.bot.entities.input.teambattle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.enums.Currency;
import fr.lewon.web.bot.entities.input.Action;

public abstract class ActionTeamBattle extends Action {

	@FUEMember("battle_type")
	private String battleType;
	
	@FUEMember
	private String currency;
	
	@FUEMember("defender_id")
	private Integer defenderId;
	
	@FUEMember("attacker%5Bteam%5D%5B%5D")
	private List<Integer> attackerTeam;
	
	public ActionTeamBattle(String action, String battleType, Currency currency, Integer defenderId, Integer... attackerTeam) {
		this(action, battleType, currency, defenderId, Arrays.asList(attackerTeam));
	}
	
	public ActionTeamBattle(String action, String battleType, Currency currency, Integer defenderId, List<Integer> attackerTeam) {
		super("TeamBattle", action);
		this.battleType = battleType;
		this.currency = currency.getStrValue();
		this.defenderId = defenderId;
		this.attackerTeam = new ArrayList<>(attackerTeam);
	}

}
