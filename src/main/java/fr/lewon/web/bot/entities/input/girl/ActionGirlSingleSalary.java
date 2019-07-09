package fr.lewon.web.bot.entities.input.girl;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public class ActionGirlSingleSalary extends ActionGirl {

	@FUEMember
	private int which;

	public ActionGirlSingleSalary(int which) {
		super("get_salary");
		this.which = which;
	}

}
