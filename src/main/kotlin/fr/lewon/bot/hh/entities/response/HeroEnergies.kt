package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty

class HeroEnergies {
    @JsonProperty("hero_level")
    var heroLevel: Int? = null
    @JsonProperty
    var quest: Energy? = null
    @JsonProperty
    var fight: Energy? = null
    @JsonProperty
    var challenge: Energy? = null
    @JsonProperty
    var kiss: Energy? = null
}

class Energy {
    @JsonProperty("amount")
    var amount: Int? = null
    @JsonProperty("max_amount")
    var maxAmount: Int? = null
    @JsonProperty("update_ts")
    var updateTs: Long? = null
    @JsonProperty("seconds_per_point")
    var secondsPerPoint: Long? = null
    @JsonProperty("next_refresh_ts")
    var nextRefreshTs: Long? = null
    @JsonProperty("recharge_time")
    var rechargeTime: Long? = null
}