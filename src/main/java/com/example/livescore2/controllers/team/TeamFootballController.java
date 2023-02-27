package com.example.livescore2.controllers.team;

import com.example.core.controller.FootballController;
import com.example.livescore2.web.teams.SaveTeamDTO;
import com.example.livescore2.web.teams.TeamDTO;

public interface TeamFootballController extends FootballController<TeamDTO, SaveTeamDTO,Long> {
}
