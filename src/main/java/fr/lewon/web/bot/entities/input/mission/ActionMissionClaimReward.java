package fr.lewon.web.bot.entities.input.mission;

public class ActionMissionClaimReward extends ActionMission {

	public ActionMissionClaimReward(Long idMission, Long idMemberMission) {
		super("claim_reward", idMission, idMemberMission);
	}

}
