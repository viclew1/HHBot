package fr.lewon.web.bot.entities.input.champions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.lewon.web.bot.util.BodyMember;

public class ActionChampionsTeamDraft extends ActionChampions {

	@BodyMember("id_champion")
	private Integer idChampion;
	
	@BodyMember
	private String namespace = "h%5CChampions";
	
	@BodyMember(value = "girls_to_keep%5B%5D")
	private List<Integer> girlsToKeep;
	
	public ActionChampionsTeamDraft(Integer idChampion, Integer... girlsToKeep) {
		this(idChampion, Arrays.asList(girlsToKeep));
	}
	
	public ActionChampionsTeamDraft(Integer idChampion, List<Integer> girlsToKeep) {
		super("team_draft");
		this.idChampion = idChampion;
		this.girlsToKeep = new ArrayList<>(girlsToKeep);
	}

}
