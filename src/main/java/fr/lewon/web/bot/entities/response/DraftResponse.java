package fr.lewon.web.bot.entities.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.lewon.web.bot.entities.girls.Girl;

public class DraftResponse extends Response {

	@JsonProperty("teamArray")
	private List<Girl> team;
	
	@JsonProperty
	private Boolean canDraft;

	@JsonProperty
	private Integer freeDrafts;

	@JsonProperty
	private Integer priceDraft;

	public List<Girl> getTeam() {
		return team;
	}

	public void setTeam(List<Girl> team) {
		this.team = team;
	}

	public Boolean getCanDraft() {
		return canDraft;
	}

	public void setCanDraft(Boolean canDraft) {
		this.canDraft = canDraft;
	}

	public Integer getFreeDrafts() {
		return freeDrafts;
	}

	public void setFreeDrafts(Integer freeDrafts) {
		this.freeDrafts = freeDrafts;
	}

	public Integer getPriceDraft() {
		return priceDraft;
	}

	public void setPriceDraft(Integer priceDraft) {
		this.priceDraft = priceDraft;
	}
	
}
