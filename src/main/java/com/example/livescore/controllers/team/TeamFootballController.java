package com.example.livescore.controllers.team;

import com.example.core.controller.FootballController;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;

public interface TeamFootballController extends FootballController<TeamDTO, SaveTeamDTO, Long> {
}
