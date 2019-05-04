package fr.lewon.web.bot.entities.girls;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.quests.QuestPremise;

public class GirlQuests {

	@JsonProperty("for_upgrade")
	private QuestPremise forUpgrade;

	public QuestPremise getForUpgrade() {
		return forUpgrade;
	}

	public void setForUpgrade(QuestPremise forUpgrade) {
		this.forUpgrade = forUpgrade;
	}

}
