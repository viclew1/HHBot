package fr.lewon.web.bot.entities.input.contest;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public class ActionContestGiveReward extends ActionContest {

	@FUEMember("id_contest")
	private Integer idContest;
	
	public ActionContestGiveReward(Integer idContest) {
		super("give_reward");
		this.idContest = idContest;
	}

}
