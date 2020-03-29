package fr.lewon.bot.hh.entities.response

import com.fasterxml.jackson.annotation.JsonProperty
import fr.lewon.bot.hh.entities.quests.QuestPremise

class UserInfos {
    @JsonProperty("energy_quest")
    var energyQuest: Int? = null
    @JsonProperty("energy_fight")
    var energyFight: Int? = null
    @JsonProperty("energy_challenge")
    var energyChallenge: Int? = null
    @JsonProperty("soft_currency")
    var softCurrency: Int? = null
    @JsonProperty("hard_currency")
    var hardCurrency: Int? = null
    @JsonProperty("energy_quest_max")
    var energyQuestMax: Int? = null
    @JsonProperty("energy_fight_max")
    var energyFightMax: Int? = null
    @JsonProperty("energy_challenge_max")
    var energyChallengeMax: Int? = null
    @JsonProperty
    var questing: QuestPremise? = null

}