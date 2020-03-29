package fr.lewon.bot.hh.entities.shop

import com.fasterxml.jackson.annotation.JsonProperty

class Item {
    @JsonProperty("id_item")
    var id: String? = null
    @JsonProperty
    var type: String? = null
    @JsonProperty
    var price: Int? = null

}