package fr.lewon.bot.hh.entities.activities

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.girls.Girl

class PlaceOfPower {
    @JsonProperty("id_places_of_power")
    var idPlaceOfPower: Int? = null
    @JsonProperty("class")
    var girlClass: Int? = null
    @JsonProperty("remaining_time")
    var remainingTime: Long? = null
    @JsonProperty("level_power")
    var levelPower: Int? = null
    @JsonProperty("max_team_power")
    var maxTeamPower: Int? = null
    @JsonProperty
    var status: String? = null
    @JsonProperty
    var girls: List<Girl> = emptyList()
}