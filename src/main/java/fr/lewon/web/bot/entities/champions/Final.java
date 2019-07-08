package fr.lewon.web.bot.entities.champions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Final {

	@JsonProperty("defender_impression")
	private Integer defenderImpression;
	@JsonProperty("attacker_ego")
	private Integer attackerEgo;
	@JsonProperty
	private Winner winner;

	
	public Integer getDefenderImpression() {
		return defenderImpression;
	}
	public void setDefenderImpression(Integer defenderImpression) {
		this.defenderImpression = defenderImpression;
	}
	public Integer getAttackerEgo() {
		return attackerEgo;
	}
	public void setAttackerEgo(Integer attackerEgo) {
		this.attackerEgo = attackerEgo;
	}
	public Winner getWinner() {
		return winner;
	}
	public void setWinner(Winner winner) {
		this.winner = winner;
	}
	
}