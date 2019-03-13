package fr.lewon.web.bot.entities.input.quest;

import fr.lewon.web.bot.util.BodyMember;

public class ActionQuestNext extends ActionQuest {

	@BodyMember("id_quest")
	private Long idQuest;

	public ActionQuestNext(Long idQuest) {
		super("next");
		this.idQuest = idQuest;
	}

}
