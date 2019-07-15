package fr.lewon.web.bot.entities.input.battle;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.battle.BattleMob;

public class ActionBattleMob extends ActionBattle {

	@FUEMember("who[id_troll]")
	private String whoIdTroll;

	@FUEMember("who[id_world]")
	private String whoIdWorld;

	public ActionBattleMob(BattleMob battleMob) {
		super(battleMob);
		this.whoIdTroll = battleMob.getIdTroll();
		this.whoIdWorld = battleMob.getIdWorld();
	}
}
