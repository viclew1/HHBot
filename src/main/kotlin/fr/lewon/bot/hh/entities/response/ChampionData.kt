package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.champions.Champion
import fr.lewon.bot.hh.entities.girls.Girl

class ChampionData {
    @JsonProperty
    var champion: Champion? = null
    @JsonProperty
    var team: List<Girl> = emptyList()
    @JsonProperty
    var isCanDraft = false
    @JsonProperty
    var freeDrafts: Int? = null
    @JsonProperty
    var priceEnergy: Int? = null

}