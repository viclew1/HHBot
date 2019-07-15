package fr.lewon.web.bot.entities.input.battle;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.battle.BattlePlayer;

public class ActionBattlePlayer extends ActionBattle {

	@FUEMember("who[id_member]")
	private String whoIdMember;

	@FUEMember("who[id_arena]")
	private String whoIdArena;

	public ActionBattlePlayer(BattlePlayer battlePlayer) {
		super(battlePlayer);
		this.whoIdMember = battlePlayer.getIdMember();
		this.whoIdArena = battlePlayer.getIdArena();
	}

}
