package fr.lewon.bot.hh.entities.input.mission

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.input.Action

abstract class ActionMission(action: String, idMission: Long?, idMemberMission: Long?) : Action("Missions", action) {

    @JsonProperty("id_mission")
    val idMission: Long? = idMission
    @JsonProperty("id_member_mission")
    val idMemberMission: Long? = idMemberMission
}