package fr.lewon.web.bot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.quests.QuestPremise;

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

	@JsonProperty
	private QuestPremise questing;

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

	public Integer getEnergyQuestMax() {
		return energyQuestMax;
	}

	public void setEnergyQuestMax(Integer energyQuestMax) {
		this.energyQuestMax = energyQuestMax;
	}

	public Integer getEnergyFightMax() {
		return energyFightMax;
	}

	public void setEnergyFightMax(Integer energyFightMax) {
		this.energyFightMax = energyFightMax;
	}

	public Integer getEnergyChallengeMax() {
		return energyChallengeMax;
	}

	public void setEnergyChallengeMax(Integer energyChallengeMax) {
		this.energyChallengeMax = energyChallengeMax;
	}

	public QuestPremise getQuesting() {
		return questing;
	}

	public void setQuesting(QuestPremise questing) {
		this.questing = questing;
	}

}
