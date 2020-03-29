package fr.lewon.bot.hh.entities.input.shop

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.input.Action
import fr.lewon.bot.hh.entities.shop.Item

class ActionBuyItem(item: Item) : Action("Item", "buy") {
    @JsonProperty("id_item")
    val idItem: String? = item.id
    @JsonProperty
    val type: String? = item.type
    @JsonProperty
    val who = 1

}