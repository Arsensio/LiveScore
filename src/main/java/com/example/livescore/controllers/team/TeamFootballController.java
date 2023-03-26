package com.example.livescore.controllers.team;

import com.example.core.controller.FootballController;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamFootballController extends FootballController<TeamDTO, SaveTeamDTO, Long> {

    ResponseEntity<List<TeamDTO>> findAllTeamByGroupId(long groupId);
}
