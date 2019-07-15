package fr.lewon.web.bot.entities.input.battle;

import java.math.BigDecimal;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.battle.AbstractBattleOpponent;
import fr.lewon.web.bot.entities.input.Action;

public abstract class ActionBattle extends Action {

	@FUEMember("who[orgasm]")
	private Integer whoOrgasm;

	@FUEMember("who[ego]")
	private BigDecimal whoEgo;

	@FUEMember("who[x]")
	private Integer whoX;

	@FUEMember("who[curr_ego]")
	private BigDecimal whoCurrEgo;

	@FUEMember("who[nb_org]")
	private Integer whoNbOrg;

	@FUEMember("who[figure]")
	private Integer whoFigure;

	@FUEMember
	private Integer autoFight;


	public ActionBattle() {
		this(null);
	}

	public ActionBattle(AbstractBattleOpponent opponent) {
		super("Battle", "fight");
		if (opponent != null) {
			this.whoOrgasm = opponent.getOrgasm();
			this.whoEgo = opponent.getEgo();
			this.whoX = opponent.getX();
			this.whoCurrEgo = opponent.getCurrEgo();
			this.whoNbOrg = opponent.getNbOrg();
			this.whoFigure = opponent.getFigure();
		}
		this.autoFight = 0;
	}


	public Integer getWhoOrgasm() {
		return whoOrgasm;
	}

	public void setWhoOrgasm(Integer whoOrgasm) {
		this.whoOrgasm = whoOrgasm;
	}

	public BigDecimal getWhoEgo() {
		return whoEgo;
	}

	public void setWhoEgo(BigDecimal whoEgo) {
		this.whoEgo = whoEgo;
	}

	public Integer getWhoX() {
		return whoX;
	}

	public void setWhoX(Integer whoX) {
		this.whoX = whoX;
	}

	public BigDecimal getWhoCurrEgo() {
		return whoCurrEgo;
	}

	public void setWhoCurrEgo(BigDecimal whoCurrEgo) {
		this.whoCurrEgo = whoCurrEgo;
	}

	public Integer getWhoNbOrg() {
		return whoNbOrg;
	}

	public void setWhoNbOrg(Integer whoNbOrg) {
		this.whoNbOrg = whoNbOrg;
	}

	public Integer getWhoFigure() {
		return whoFigure;
	}

	public void setWhoFigure(Integer whoFigure) {
		this.whoFigure = whoFigure;
	}

	public Integer getAutoFight() {
		return autoFight;
	}

	public void setAutoFight(Integer autoFight) {
		this.autoFight = autoFight;
	}




}
