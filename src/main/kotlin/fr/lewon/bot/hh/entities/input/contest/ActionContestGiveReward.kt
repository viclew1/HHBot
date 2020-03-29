package fr.lewon.bot.hh.entities.input.contest

import com.fasterxml.jackson.annotation.JsonProperty

class ActionContestGiveReward(idContest: Int) : ActionContest("give_reward") {

    @JsonProperty("id_contest")
    val idContest: Int = idContest

}