package fr.lewon.web.bot.entities.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BattlePlayer extends AbstractBattleOpponent {

	@JsonProperty("id_member")
	private String idMember;

	@JsonProperty("id_arena")
	private String idArena;


	public String getIdMember() {
		return idMember;
	}
	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}
	public String getIdArena() {
		return idArena;
	}
	public void setIdArena(String idArena) {
		this.idArena = idArena;
	}

}
