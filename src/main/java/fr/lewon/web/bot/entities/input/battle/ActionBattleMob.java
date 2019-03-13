package fr.lewon.web.bot.entities.input.battle;

import fr.lewon.web.bot.entities.input.others.battle.BattleMob;
import fr.lewon.web.bot.util.BodyMember;

public class ActionBattleMob extends ActionBattle {

	@BodyMember("who[id_troll]")
	private String whoIdTroll;

	@BodyMember("who[id_world]")
	private String whoIdWorld;

	public ActionBattleMob(BattleMob battleMob) {
		super(battleMob);
		this.whoIdTroll = battleMob.getIdTroll();
		this.whoIdWorld = battleMob.getIdWorld();
	}
}
