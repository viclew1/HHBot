package fr.lewon.web.bot.entities.input.mission;

import fr.lewon.web.bot.entities.input.Action;
import fr.lewon.web.bot.util.BodyMember;

public abstract class ActionMission extends Action {

	@BodyMember("id_mission")
	private Long idMission;

	@BodyMember("id_member_mission")
	private Long idMemberMission;

	public ActionMission(String action, Long idMission, Long idMemberMission) {
		super("Missions", action);
		this.idMission = idMission;
		this.idMemberMission = idMemberMission;
	}

}
