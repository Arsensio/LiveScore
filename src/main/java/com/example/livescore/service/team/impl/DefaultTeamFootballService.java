package com.example.livescore.service.team.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.GroupInfoEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.repository.GroupInfoRepository;
import com.example.livescore.repository.TeamRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import com.example.livescore.web.teams.TeamWithPlayersDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.livescore.enums.GroupStatusEnum.IN_PROGRESS;

@Service
public class DefaultTeamFootballService
        extends AbstractFootballService<TeamEntity, TeamDTO, SaveTeamDTO, Long, TeamRepository>
        implements TeamFootballService {

    private final TeamStatisticsService teamStatisticsService;
    private final TournamentService tournamentService;
    private final GroupInfoRepository groupInfoRepository;
    private final GroupService groupService;

    public DefaultTeamFootballService(TeamRepository repository, TeamStatisticsService
            teamStatisticsService, TournamentService tournamentService, GroupInfoRepository groupInfoRepository, GroupService groupService) {
        super(repository);
        this.teamStatisticsService = teamStatisticsService;
        this.tournamentService = tournamentService;
        this.groupInfoRepository = groupInfoRepository;
        this.groupService = groupService;
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
        TournamentEntity tournament = tournamentService.findEntityById(saveTeamDTO.getTournamentId());
        teamStatisticsService.save(tournament, savedTeam);

        if (tournament.getTournamentType().equals("LEAGUE")) {
            groupInfoRepository.saveAndFlush(getDefaultGroupInfoForLeague(savedTeam, tournament));
        }

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
    public List<TeamDTO> findAllTeamByTournamentId(long groupId) {
        return teamStatisticsService.findAllTeamByTournamentId(groupId);
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

    // todo: искать не только по тим нейм но и по турнаменту
    @Override
    public TeamDTO findTeamByNameInTournament(String teamName, Long tournamentId) {
        TeamEntity team = repository.findTeamEntityByTeamName(teamName);
        if (team == null) {
            throw new RuntimeException("Команды с названием " + teamName + " не существует!");
        }
        return team.toDTO();
    }

    @Override
    public String getTeamNameById(Long teamId) {
        return repository.getTeamNameById(teamId);
    }

    @Override
    public List<TeamWithPlayersDto> findAllTeamsAndItsPlayers() {
        List<TeamDTO> allTeams = repository.findAll()
                .stream()
                .map(TeamEntity::toDTO)
                .toList();

        List<TeamWithPlayersDto> teamWithPlayersDto = new ArrayList<>();

//        for (TeamDTO team : allTeams) {
//            teamWithPlayersDto.add(
//                    TeamWithPlayersDto.builder()
//                            .teamId(team.getTeamId())
//                            .teamName(team.getTeamName())
//                            .teamLogo(team.getTeamLogo())
//                            .build());
//        }
        return teamWithPlayersDto;
    }


    private GroupInfoEntity getDefaultGroupInfoForLeague(TeamEntity savedTeam, TournamentEntity tournament) {
        GroupEntity group = groupService.findAllByTournamentID(tournament.getTournamentId()).get(0);
        return GroupInfoEntity.builder()
                .tournamentLogo(tournament.getTournamentLogo())
                .groupName(group.getGroupName())
                .teamName(savedTeam.getTeamName())
                .teamLogo(savedTeam.getTeamLogo())
                .gamePlayed(0)
                .winCount(0)
                .drawCount(0)
                .loseCount(0)
                .goalCount(0)
                .goalMissed(0)
                .points(0)
                .status(IN_PROGRESS.toString())
                .group(group)
                .team(savedTeam)
                .tournament(tournament)
                .build();
    }
}
