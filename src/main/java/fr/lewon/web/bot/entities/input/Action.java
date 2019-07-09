package fr.lewon.web.bot.entities.input;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public abstract class Action {

	@FUEMember("class")
	private String clazz;

	@FUEMember
	private String action;

	public Action(String clazz, String action) {
		this.clazz = clazz;
		this.action = action;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
