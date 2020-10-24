package fr.lewon.bot.hh.entities.input.powerplaces

import com.fasterxml.jackson.annotation.JsonProperty

class ActionPlaceOfPowerStart(
        idPlaceOfPower: Int,
        @field:JsonProperty(value = "selected_girls[]") val selectedGirls: List<Int>
) : ActionPlaceOfPower(idPlaceOfPower, "start")