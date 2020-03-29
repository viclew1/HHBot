package fr.lewon.bot.hh.entities.input.battle

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.battle.AbstractBattleOpponent
import fr.lewon.bot.hh.entities.input.Action
import java.math.BigDecimal

abstract class ActionBattle @JvmOverloads constructor(opponent: AbstractBattleOpponent? = null) : Action("Battle", "fight") {
    @JsonProperty("who[orgasm]")
    val whoOrgasm: Int? = opponent?.orgasm
    @JsonProperty("who[ego]")
    val whoEgo: BigDecimal? = opponent?.ego
    @JsonProperty("who[x]")
    val whoX: Int? = opponent?.x
    @JsonProperty("who[curr_ego]")
    val whoCurrEgo: BigDecimal? = opponent?.currEgo
    @JsonProperty("who[nb_org]")
    val whoNbOrg: Int? = opponent?.nbOrg
    @JsonProperty("who[figure]")
    val whoFigure: Int? = opponent?.figure
    @JsonProperty
    val autoFight: Int = 0
}