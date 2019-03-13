package fr.lewon.web.bot.entities.input.mission;

public class ActionMissionStartMission extends ActionMission {

	public ActionMissionStartMission(Long idMission, Long idMemberMission) {
		super("start_mission", idMission, idMemberMission);
	}

}
