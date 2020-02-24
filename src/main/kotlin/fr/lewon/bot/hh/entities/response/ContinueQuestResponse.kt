package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.diverse.Cost

class ContinueQuestResponse : Response() {
    @JsonProperty("id_quest")
    var idQuest: Long? = null
    @JsonProperty
    var cost: Cost? = null

}