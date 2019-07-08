package fr.lewon.web.bot.entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.champions.Final;

public class TeamBattleResponse extends Response {

	@JsonProperty("final")
	private Final fnl;

	public Final getFnl() {
		return fnl;
	}

	public void setFnl(Final fnl) {
		this.fnl = fnl;
	}
	
}