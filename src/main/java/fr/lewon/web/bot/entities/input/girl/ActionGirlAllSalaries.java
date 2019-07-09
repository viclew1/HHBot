package fr.lewon.web.bot.entities.input.girl;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public class ActionGirlAllSalaries extends ActionGirl {

	@FUEMember
	private String where = "home";

	@FUEMember
	private boolean mc = true;

	public ActionGirlAllSalaries() {
		super("get_all_salaries");
	}

}
