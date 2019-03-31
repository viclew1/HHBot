package fr.lewon.web.bot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfos {

	@JsonProperty("energy_quest")
	private Integer energyQuest;

	@JsonProperty("energy_fight")
	private Integer energyFight;

	@JsonProperty("energy_challenge")
	private Integer energyChallenge;

	@JsonProperty("soft_currency")
	private Integer softCurrency;

	@JsonProperty("hard_currency")
	private Integer hardCurrency;

	@JsonProperty("energy_quest_max")
	private Integer energyQuestMax;

	@JsonProperty("energy_fight_max")
	private Integer energyFightMax;

	@JsonProperty("energy_challenge_max")
	private Integer energyChallengeMax;

	public Integer getEnergyQuest() {
		return energyQuest;
	}

	public void setEnergyQuest(Integer energyQuest) {
		this.energyQuest = energyQuest;
	}

	public Integer getEnergyFight() {
		return energyFight;
	}

	public void setEnergyFight(Integer energyFight) {
		this.energyFight = energyFight;
	}

	public Integer getEnergyChallenge() {
		return energyChallenge;
	}

	public void setEnergyChallenge(Integer energyChallenge) {
		this.energyChallenge = energyChallenge;
	}

	public Integer getSoftCurrency() {
		return softCurrency;
	}

	public void setSoftCurrency(Integer softCurrency) {
		this.softCurrency = softCurrency;
	}

	public Integer getHardCurrency() {
		return hardCurrency;
	}

	public void setHardCurrency(Integer hardCurrency) {
		this.hardCurrency = hardCurrency;
	}

}
