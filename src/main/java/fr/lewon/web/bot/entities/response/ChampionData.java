package fr.lewon.web.bot.entities.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.champions.Champion;
import fr.lewon.web.bot.entities.girls.Girl;

public class ChampionData {

	@JsonProperty
	private Champion champion;
	
	@JsonProperty
	private List<Girl> team;
	
	@JsonProperty
	private boolean canDraft;
	
	@JsonProperty
	private Integer freeDrafts;

	@JsonProperty
	private Integer priceEnergy;

	
	public Champion getChampion() {
		return champion;
	}

	public void setChampion(Champion champion) {
		this.champion = champion;
	}

	public List<Girl> getTeam() {
		return team;
	}

	public void setTeam(List<Girl> team) {
		this.team = team;
	}

	public boolean isCanDraft() {
		return canDraft;
	}

	public void setCanDraft(boolean canDraft) {
		this.canDraft = canDraft;
	}

	public Integer getFreeDrafts() {
		return freeDrafts;
	}

	public void setFreeDrafts(Integer freeDrafts) {
		this.freeDrafts = freeDrafts;
	}

	public Integer getPriceEnergy() {
		return priceEnergy;
	}

	public void setPriceEnergy(Integer priceEnergy) {
		this.priceEnergy = priceEnergy;
	}
	
}
