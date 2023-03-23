package com.example.livescore.service.team_statistics.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.models.TeamStatisticsEntity;
import com.example.livescore.models.TeamStatisticsEntityPK;
import com.example.livescore.repository.GroupRepository;
import com.example.livescore.repository.ProtocolRepository;
import com.example.livescore.repository.TeamStatisticsRepository;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.web.teamStatistics.DistinctTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.SaveTeamStatisticsDTO;
import com.example.livescore.web.teamStatistics.StatisticDTO;
import com.example.livescore.web.teamStatistics.TeamStatisticsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultTeamStatisticsService
        extends AbstractFootballService<TeamStatisticsEntity, TeamStatisticsDTO,
        SaveTeamStatisticsDTO, TeamStatisticsEntityPK, TeamStatisticsRepository>
        implements TeamStatisticsService {

    private final GroupRepository groupRepository;
    private final ProtocolRepository protocolRepository;


    public DefaultTeamStatisticsService(TeamStatisticsRepository repository, GroupRepository groupRepository, ProtocolRepository protocolRepository) {
        super(repository);
        this.groupRepository = groupRepository;
        this.protocolRepository = protocolRepository;
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByGoals(long groupId) {
        return repository.findAllByGroupIdOrderByGoalCount(groupId)
                .stream()
                .map(team -> team.toDistinctStatisticsDTO(EventNames.GOAL.getEventName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamStatisticsDTO> findTeamsSortedByPoints(long groupId) {
        List<TeamStatisticsEntity> allByGroupIdOrderByWinCount = repository.findAllByGroupIdOrderByWinCount(groupId);
        List<ProtocolEntity> allByGameStateStarted = protocolRepository.findAllByGameStateStarted();
        List<TeamStatisticsDTO> orderedByPointList = new ArrayList<>();

        for (TeamStatisticsEntity teamStatistics : allByGroupIdOrderByWinCount) {
            for (ProtocolEntity protocolEntity : allByGameStateStarted) {

                if (teamStatistics.getId().getTeam() == protocolEntity.getTeam1()) {
                    int team1Score = protocolEntity.getTeam1Score();
                    int team2Score = protocolEntity.getTeam2Score();

                    if (team1Score > team2Score) {
                        teamStatistics.setWinCount(teamStatistics.getWinCount() + 1);
                        teamStatistics.setPoints(teamStatistics.getPoints() + 3);
                    } else if (team1Score == team2Score) {
                        teamStatistics.setDrawCount(teamStatistics.getDrawCount() + 1);
                        teamStatistics.setPoints(teamStatistics.getPoints() + 1);
                    } else {
                        teamStatistics.setLoseCount(teamStatistics.getLoseCount() + 1);
                    }
                } else if (teamStatistics.getId().getTeam() == protocolEntity.getTeam2()) {
                    int team1Score = protocolEntity.getTeam1Score();
                    int team2Score = protocolEntity.getTeam2Score();

                    if (team2Score > team1Score) {
                        teamStatistics.setWinCount(teamStatistics.getWinCount() + 1);
                        teamStatistics.setPoints(teamStatistics.getPoints() + 3);
                    } else if (team1Score == team2Score) {
                        teamStatistics.setDrawCount(teamStatistics.getDrawCount() + 1);
                        teamStatistics.setPoints(teamStatistics.getPoints() + 1);
                    } else {
                        teamStatistics.setLoseCount(teamStatistics.getLoseCount() + 1);
                    }
                }
            }
            orderedByPointList.add(teamStatistics.toDTO());
        }

        Collections.sort(orderedByPointList);

        return orderedByPointList;
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByRedCards(long groupId) {
        List<StatisticDTO> allByGroupIdOrderByRedCard = repository.findAllByGroupIdOrderByRedCard(groupId);

        return allByGroupIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.RED_CARD))
                .collect(Collectors.toList());
    }

    @Override
    public List<DistinctTeamStatisticsDTO> findTeamsSortedByYellowCard(long groupId) {
        List<StatisticDTO> allByGroupIdOrderByRedCard = repository.findAllByGroupIdOrderByYellowCard(groupId);

        return allByGroupIdOrderByRedCard
                .stream()
                .map(statisticDTO ->
                        statisticDTO.toDistinctTeamStatisticsDTO(EventNames.YELLOW_CARD))
                .collect(Collectors.toList());
    }


}
