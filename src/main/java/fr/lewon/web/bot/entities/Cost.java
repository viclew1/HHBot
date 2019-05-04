package fr.lewon.web.bot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cost {

	@JsonProperty("$")
	private Integer regularCost;

	@JsonProperty("HC")
	private Integer specialCost;

	@JsonProperty("*")
	private Integer energyCost;


	public Integer getRegularCost() {
		return regularCost;
	}

	public void setRegularCost(Integer regularCost) {
		this.regularCost = regularCost;
	}

	public Integer getSpecialCost() {
		return specialCost;
	}

	public void setSpecialCost(Integer specialCost) {
		this.specialCost = specialCost;
	}

	public Integer getEnergyCost() {
		return energyCost;
	}

	public void setEnergyCost(Integer energyCost) {
		this.energyCost = energyCost;
	}

}
