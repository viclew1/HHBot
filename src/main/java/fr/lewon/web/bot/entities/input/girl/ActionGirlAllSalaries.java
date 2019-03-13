package fr.lewon.web.bot.entities.input.girl;

import fr.lewon.web.bot.util.BodyMember;

public class ActionGirlAllSalaries extends ActionGirl {

	@BodyMember
	private String where = "home";

	@BodyMember
	private boolean mc = true;

	public ActionGirlAllSalaries() {
		super("get_all_salaries");
	}

}
