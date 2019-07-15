package fr.lewon.web.bot.entities.battle;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractBattleOpponent {

	@JsonProperty
	private Integer orgasm;

	@JsonProperty
	private BigDecimal ego;

	@JsonProperty
	private Integer x;

	@JsonProperty("curr_ego")
	private BigDecimal currEgo;

	@JsonProperty("nb_org")
	private Integer nbOrg;

	@JsonProperty
	private Integer figure;

	public Integer getOrgasm() {
		return orgasm;
	}

	public void setOrgasm(Integer orgasm) {
		this.orgasm = orgasm;
	}

	public BigDecimal getEgo() {
		return ego;
	}

	public void setEgo(BigDecimal ego) {
		this.ego = ego;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public BigDecimal getCurrEgo() {
		return currEgo;
	}

	public void setCurrEgo(BigDecimal currEgo) {
		this.currEgo = currEgo;
	}

	public Integer getNbOrg() {
		return nbOrg;
	}

	public void setNbOrg(Integer nbOrg) {
		this.nbOrg = nbOrg;
	}

	public Integer getFigure() {
		return figure;
	}

	public void setFigure(Integer figure) {
		this.figure = figure;
	}

}
