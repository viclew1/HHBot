package fr.lewon.web.bot.entities.quests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestPremise {

	@JsonProperty("id_quest")
	private Long idQuest;

	public Long getIdQuest() {
		return idQuest;
	}

	public void setIdQuest(Long idQuest) {
		this.idQuest = idQuest;
	}

}
