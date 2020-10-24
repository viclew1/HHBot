package fr.lewon.bot.hh.entities.input.powerplaces

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.input.Action

abstract class ActionPlaceOfPower(
        @field:JsonProperty(value = "id_place_of_power") val idPlaceOfPower: Int,
        action: String
) : Action("PlaceOfPower", action) {
    @JsonProperty
    val namespace = "h\\PlacesOfPower"
}