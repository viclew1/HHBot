package fr.lewon.web.bot.entities.input.girl;

import fr.lewon.web.bot.util.BodyMember;

public class ActionGirlSingleSalary extends ActionGirl {

	@BodyMember
	private int which;

	public ActionGirlSingleSalary(int which) {
		super("get_salary");
		this.which = which;
	}

}
