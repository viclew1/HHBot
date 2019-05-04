package fr.lewon.web.bot.entities.quests;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.Cost;

public class QuestStep {

	@JsonProperty
	private Cost cost;

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

}
