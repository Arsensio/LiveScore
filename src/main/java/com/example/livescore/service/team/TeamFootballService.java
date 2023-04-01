package com.example.livescore.service.team;

import com.example.core.service.FootballService;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;

import java.util.List;

public interface TeamFootballService extends FootballService<TeamDTO, SaveTeamDTO, Long> {

    List<TeamDTO> findAllTeamByGroupId(long groupId);

    TeamEntity findEntityById(long id);

    TeamDTO findTeamByName(String teamName);
}
