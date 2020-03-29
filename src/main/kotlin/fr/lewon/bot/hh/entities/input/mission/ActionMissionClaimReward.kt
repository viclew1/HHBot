package fr.lewon.bot.hh.entities.input.mission

import fr.lewon.bot.hh.entities.activities.Mission

class ActionMissionClaimReward(mission: Mission) : ActionMission("claim_reward", mission.idMission, mission.idMemberMission)