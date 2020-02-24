package fr.lewon.bot.hh.entities.girls

import com.fasterxml.jackson.annotation.JsonProperty

class Girl {
    @JsonProperty("id_girl")
    var id: Int = 0
    @JsonProperty("class")
    var fightClass: Int? = null
    @JsonProperty
    var rarity: String? = null
    @JsonProperty
    var level: Int? = null
    @JsonProperty
    var own: Boolean? = null
    @JsonProperty("Xp")
    var xp: XpInfos? = null
    @JsonProperty("Affection")
    var affection: AffectionInfos? = null
    @JsonProperty("can_upgrade")
    var isCanUpgrade = false
    @JsonProperty("pay_in")
    var payIn: Int = 0
    @JsonProperty
    var quests: GirlQuests? = null
    @JsonProperty
    var damage: Int? = null

}