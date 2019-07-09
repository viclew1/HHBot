package fr.lewon.web.bot.entities.input.mission;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.input.Action;

public abstract class ActionMission extends Action {

	@FUEMember("id_mission")
	private Long idMission;

	@FUEMember("id_member_mission")
	private Long idMemberMission;

	public ActionMission(String action, Long idMission, Long idMemberMission) {
		super("Missions", action);
		this.idMission = idMission;
		this.idMemberMission = idMemberMission;
	}

}
