package fr.lewon.web.bot.entities.input.stat;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.input.Action;

public class ActionUpgradeStat extends Action {

	@FUEMember
	private Integer carac;
	
	public ActionUpgradeStat(Integer carac) {
		super("Hero", "update_stats");
		this.carac = carac;
	}

}
