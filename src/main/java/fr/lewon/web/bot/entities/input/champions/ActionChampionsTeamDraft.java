package fr.lewon.web.bot.entities.input.champions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.lewon.bot.http.body.urlencoded.FUEMember;

public class ActionChampionsTeamDraft extends ActionChampions {

	@FUEMember("id_champion")
	private Integer idChampion;
	
	@FUEMember
	private String namespace = "h%5CChampions";
	
	@FUEMember(value = "girls_to_keep%5B%5D")
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
