package fr.lewon.bot.hh.entities.input.mission

import fr.lewon.bot.hh.entities.activities.Mission

class ActionMissionStartMission(mission: Mission) : ActionMission("start_mission", mission.idMission, mission.idMemberMission)