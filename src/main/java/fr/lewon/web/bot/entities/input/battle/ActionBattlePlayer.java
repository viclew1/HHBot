package fr.lewon.web.bot.entities.input.battle;

import fr.lewon.web.bot.entities.input.others.battle.BattlePlayer;
import fr.lewon.web.bot.util.BodyMember;

public class ActionBattlePlayer extends ActionBattle {

	@BodyMember("who[id_member]")
	private String whoIdMember;

	@BodyMember("who[id_arena]")
	private String whoIdArena;

	public ActionBattlePlayer(BattlePlayer battlePlayer) {
		super(battlePlayer);
		this.whoIdMember = battlePlayer.getIdMember();
		this.whoIdArena = battlePlayer.getIdArena();
	}

}
