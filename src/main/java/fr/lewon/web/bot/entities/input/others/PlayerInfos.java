package fr.lewon.web.bot.entities.input.others;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public class PlayerInfos {

	@FUEMember
	private String login;

	@FUEMember("password")
	private String password;

	@FUEMember("stay_online")
	private String stayOnline = "1";

	@FUEMember("module")
	private String module = "Member";

	@FUEMember("action")
	private String action = "form_log_in";

	@FUEMember("call")
	private String call = "Member";


	public PlayerInfos(String login, String password) {
		this.login = login;
		this.password = password;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStayOnline() {
		return stayOnline;
	}

	public void setStayOnline(String stayOnline) {
		this.stayOnline = stayOnline;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}


}
