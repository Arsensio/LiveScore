package com.example.livescore.service.team.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.repository.TeamRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.player.PlayerService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import com.example.livescore.web.teams.TeamWithPlayersDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTeamFootballService
        extends AbstractFootballService<TeamEntity, TeamDTO, SaveTeamDTO, Long, TeamRepository>
        implements TeamFootballService {

    private final GroupService groupService;
    private final TeamStatisticsService teamStatisticsService;
    private final PlayerService playerService;

    public DefaultTeamFootballService(TeamRepository repository, GroupService groupService, TeamStatisticsService
            teamStatisticsService, PlayerService playerService) {
        super(repository);
        this.groupService = groupService;
        this.teamStatisticsService = teamStatisticsService;
        this.playerService = playerService;
    }

    @Override
    @Transactional
    public TeamDTO save(SaveTeamDTO saveTeamDTO) {
        TeamEntity savedTeam = repository.save(new TeamEntity(
                        null,
                        saveTeamDTO.getTeamName(),
                        saveTeamDTO.getTeamLogo(),
                        null
                )
        );
        GroupEntity group = groupService.findEntityById(saveTeamDTO.getGroupId());
        teamStatisticsService.save(group, savedTeam);

        return savedTeam.toDTO();
    }

    @Override
    public TeamDTO update(Long id, SaveTeamDTO saveTeamDTO) {
        repository.findById(id).ifPresentOrElse(team -> {
            team.setTeamName(saveTeamDTO.getTeamName());
            team.setTeamLogo(saveTeamDTO.getTeamLogo());
            repository.saveAndFlush(team);
        }, () -> {
            throw ResourceNotFoundException.build(id, "Team");
        });
        return repository.findById(id).get().toDTO();
    }

    @Override
    public List<TeamDTO> findAllTeamByGroupId(long groupId) {
        return teamStatisticsService.findAllTeamByGroupId(groupId);
    }

    @Override
    public TeamEntity findEntityById(long id) {
        Optional<TeamEntity> referenceById = repository.findById(id);
        if (referenceById.isEmpty()) {
            throw ResourceNotFoundException.build(id, "TeamEntity");
        } else {
            return referenceById.get();
        }
    }


    @Override
    public TeamDTO findTeamByName(String teamName) {
        TeamEntity team = repository.findTeamEntityByTeamName(teamName);
        if (team == null) {
            throw new RuntimeException("Команды с названием " + teamName + " не существует!");
        }
        return team.toDTO();
    }

    @Override
    public List<TeamWithPlayersDto> findAllTeamsAndItsPlayers() {
        List<TeamDTO> allTeams = repository.findAll()
                .stream()
                .map(TeamEntity::toDTO)
                .collect(Collectors.toList());

        List<TeamWithPlayersDto> teamWithPlayersDto = new ArrayList<>();

        for (TeamDTO team : allTeams) {
            teamWithPlayersDto.add(
                    TeamWithPlayersDto.builder()
                            .teamId(team.getTeamId())
                            .teamName(team.getTeamName())
                            .teamLogo(team.getTeamLogo())
                            .players(playerService.findAllPlayersOfTeam(team.getTeamId()))
                            .build());
        }
        return teamWithPlayersDto;
    }
}
