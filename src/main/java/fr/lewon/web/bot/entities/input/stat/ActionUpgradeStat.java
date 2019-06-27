package fr.lewon.web.bot.entities.input.stat;

import fr.lewon.web.bot.entities.input.Action;
import fr.lewon.web.bot.util.BodyMember;

public class ActionUpgradeStat extends Action {

	@BodyMember
	private Integer carac;
	
	public ActionUpgradeStat(Integer carac) {
		super("Hero", "update_stats");
		this.carac = carac;
	}

}
