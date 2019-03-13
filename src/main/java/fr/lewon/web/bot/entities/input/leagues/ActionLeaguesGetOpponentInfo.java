package fr.lewon.web.bot.entities.input.leagues;

import fr.lewon.web.bot.util.BodyMember;

public class ActionLeaguesGetOpponentInfo extends ActionLeagues {

	@BodyMember("opponent_id")
	private String opponentId;

	public ActionLeaguesGetOpponentInfo(String opponentId) {
		super("get_opponent_info");
		this.opponentId = opponentId;
	}

	public String getOpponentId() {
		return opponentId;
	}

	public void setOpponentId(String opponentId) {
		this.opponentId = opponentId;
	}

}
