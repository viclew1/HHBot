package fr.lewon.web.bot.entities.battle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TowerOfFameOpponentPremise {

	@JsonProperty("id_player")
	private String id;
	
	@JsonProperty("level")
	private int lvl;
	
	@JsonProperty("nb_challenges_played")
	private int nbChallengesPlayed;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getNbChallengesPlayed() {
		return nbChallengesPlayed;
	}

	public void setNbChallengesPlayed(int nbChallengesPlayed) {
		this.nbChallengesPlayed = nbChallengesPlayed;
	}

}
