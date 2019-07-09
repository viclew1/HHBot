package fr.lewon.web.bot.entities.input.quest;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public class ActionQuestNext extends ActionQuest {

	@FUEMember("id_quest")
	private Long idQuest;

	public ActionQuestNext(Long idQuest) {
		super("next");
		this.idQuest = idQuest;
	}

}
