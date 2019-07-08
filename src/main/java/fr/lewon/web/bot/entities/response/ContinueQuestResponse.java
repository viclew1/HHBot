package fr.lewon.web.bot.entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.diverse.Cost;

public class ContinueQuestResponse extends Response {

	@JsonProperty("id_quest")
	private Long idQuest;

	@JsonProperty
	private Cost cost;

	public Long getIdQuest() {
		return idQuest;
	}

	public void setIdQuest(Long idQuest) {
		this.idQuest = idQuest;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

}
