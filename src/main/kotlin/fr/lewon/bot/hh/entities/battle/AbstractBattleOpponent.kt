package fr.lewon.bot.hh.entities.battle

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

open class AbstractBattleOpponent {
    @JsonProperty
    var orgasm: Int? = null
    @JsonProperty
    var ego: BigDecimal? = null
    @JsonProperty
    var x: Int? = null
    @JsonProperty("curr_ego")
    var currEgo: BigDecimal? = null
    @JsonProperty("nb_org")
    var nbOrg: Int? = null
    @JsonProperty
    var figure: Int? = null

}