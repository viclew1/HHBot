package fr.lewon.bot.hh.entities.activities

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.enums.Rarity

class Mission {
    @JsonProperty("id_member_mission")
    var idMemberMission: Long? = null
    @JsonProperty("id_mission")
    var idMission: Long? = null
    @JsonProperty
    var duration: Int? = null
    @JsonProperty
    var cost: Int? = null
    @JsonProperty("remaining_time")
    var remainingTime: Int? = null
    var rarity: Rarity? = null
        private set

    fun setRarity(rarity: String) {
        this.rarity = Rarity.getValueByLabel(rarity)
    }

}