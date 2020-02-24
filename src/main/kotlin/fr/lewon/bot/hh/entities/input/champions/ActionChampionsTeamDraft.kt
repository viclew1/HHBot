package fr.lewon.bot.hh.entities.input.champions

import com.fasterxml.jackson.annotation.JsonProperty


class ActionChampionsTeamDraft(idChampion: Int, girlsToKeep: List<Int>) : ActionChampions("team_draft") {
    @JsonProperty(value = "id_champion")
    val idChampion: Int = idChampion
    @JsonProperty
    val namespace = "h%5CChampions"
    @JsonProperty(value = "girls_to_keep%5B%5D")
    val girlsToKeep: List<Int> = girlsToKeep

}