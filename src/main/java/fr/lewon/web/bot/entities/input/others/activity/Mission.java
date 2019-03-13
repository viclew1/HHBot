package fr.lewon.web.bot.entities.input.others.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.enums.Rarity;

public class Mission {

	@JsonProperty("id_member_mission")
	private Long idMemberMission;

	@JsonProperty("id_mission")
	private Long idMission;

	@JsonProperty
	private Integer duration;

	@JsonProperty
	private Integer cost;

	@JsonProperty("remaining_time")
	private Integer remainingTime;

	private Rarity rarity;
	private boolean startable;


	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = Rarity.getValueByLabel(rarity);
	}

	public Long getIdMemberMission() {
		return idMemberMission;
	}

	public void setIdMemberMission(Long idMemberMission) {
		this.idMemberMission = idMemberMission;
	}

	public Long getIdMission() {
		return idMission;
	}

	public void setIdMission(Long idMission) {
		this.idMission = idMission;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Integer remainingTime) {
		this.remainingTime = remainingTime;
	}

	public void setStartable(boolean startable) {
		this.startable = startable;
	}

	public boolean isStartable() {
		return startable;
	}

}
