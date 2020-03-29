package fr.lewon.bot.hh.entities.input.quest

import com.fasterxml.jackson.annotation.JsonProperty

class ActionQuestNext(idQuest: Long) : ActionQuest("next") {

    @JsonProperty("id_quest")
    val idQuest: Long = idQuest
}