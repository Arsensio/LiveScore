package com.example.livescore2.service;

import com.example.core.service.FootballService;
import com.example.livescore2.web.teams.SaveTeamDTO;
import com.example.livescore2.web.teams.TeamDTO;

public interface TeamFootballService extends FootballService<TeamDTO, SaveTeamDTO> {
}
