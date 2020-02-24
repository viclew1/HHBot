package fr.lewon.bot.hh.entities.input.champions

import com.fasterxml.jackson.annotation.JsonProperty


class ActionChampionsTeamDraft(idChampion: Int, girlsToKeep: List<Int>) : ActionChampions("team_draft") {
    @JsonProperty(value = "id_champion")
    val idChampion: Int = idChampion
    @JsonProperty
    val namespace = "h\\Champions"
    @JsonProperty(value = "girls_to_keep[]")
    val girlsToKeep: List<Int> = girlsToKeep

}