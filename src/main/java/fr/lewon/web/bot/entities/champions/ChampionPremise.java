package fr.lewon.web.bot.entities.champions;

public class ChampionPremise {

	private Integer championId;
	private Integer secondsToWait;

	public ChampionPremise(Integer championId, Integer secondsToWait) {
		this.championId = championId;
		this.secondsToWait = secondsToWait;
	}

	public Integer getChampionId() {
		return championId;
	}
	public void setChampionId(Integer championId) {
		this.championId = championId;
	}
	public Integer getSecondsToWait() {
		return secondsToWait;
	}
	public void setSecondsToWait(Integer secondsToWait) {
		this.secondsToWait = secondsToWait;
	}
	
}
