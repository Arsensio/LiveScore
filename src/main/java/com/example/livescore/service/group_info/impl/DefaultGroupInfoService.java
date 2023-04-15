package com.example.livescore.service.group_info.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.*;
import com.example.livescore.repository.GroupInfoRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


import static com.example.livescore.enums.GroupStatusEnum.CREATED;


@Service
public class DefaultGroupInfoService
        extends AbstractFootballService<GroupInfoEntity, GroupInfoDTO, SaveGroupInfoDTO, Long, GroupInfoRepository>
        implements GroupInfoService {

    private final TeamFootballService teamFootballService;
    private final GroupService groupService;
    private final ProtocolService protocolService;

    public DefaultGroupInfoService(GroupInfoRepository repository, TeamFootballService teamFootballService, GroupService groupService, ProtocolService protocolService) {
        super(repository);
        this.teamFootballService = teamFootballService;
        this.groupService = groupService;
        this.protocolService = protocolService;
    }

    @Override
    public GroupInfoEntity saveAfterDraw(GroupEntity group, TournamentEntity tournament, TeamEntity team) {
        return repository.save(new GroupInfoEntity(
                null,
                tournament.getTournamentLogo(),
                group.getGroupName(),
                team.getTeamName(),
                team.getTeamLogo(),
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                CREATED.toString(),
                group,
                team,
                tournament
        ));
    }

    @Override
    public GroupInfoEntity findEntityByGroupAndTeamId(TournamentEntity tournament, GroupEntity group, TeamEntity team) {
        return repository.findEntityByGroupAndTeamId(group, team, tournament);
    }

    @Override
    public GroupInfoEntity saveAndFlash(GroupInfoEntity groupInfoEntity) {
        return repository.saveAndFlush(groupInfoEntity);
    }

    @Override
    public void incrementGameCount(GroupEntity group, TeamEntity team) {
        repository.incrementGameCount(group.getGroupId(), team.getTeamId());
    }

    @Override
    public List<GroupInfoEntity> findAllByTournamentIdOrderByWinCount(Long tournament, Long group) {
        return repository.findAllByTournamentIdOrderByWinCount(tournament, group);
    }

    @Override
    public List<GroupInfoDTO> createDrawInCup(List<SaveGroupInfoDTO> list) {
        List<GroupInfoDTO> returnList = new ArrayList<>();

        for (SaveGroupInfoDTO groupInfo : list) {
            TeamEntity team = teamFootballService.findEntityById(groupInfo.getTeamId());
            GroupEntity group = groupService.findEntityById(groupInfo.getGroupId());
            TournamentEntity tournament = group.getTournament();

            GroupInfoEntity groupInfoEntity = this.saveAfterDraw(group, tournament, team);
            returnList.add(groupInfoEntity.toDTO());
        }

        return returnList;
    }

    @Override
    public List<GroupInfoDTO> findTeamsSortedByPoints(long tournamentId, long group) {
        List<GroupInfoEntity> allByTournamentIdOrderByWinCount1 = findAllByTournamentIdOrderByWinCount(tournamentId, group);

        List<ProtocolEntity> allByGameStateStarted = protocolService.findAllByGameStateStarted();
        List<GroupInfoDTO> orderedByPointList = new ArrayList<>();


        for (GroupInfoEntity groupInfo : allByTournamentIdOrderByWinCount1) {
            for (ProtocolEntity protocolEntity : allByGameStateStarted) {
                int team1Score = protocolEntity.getTeam1Score();
                int team2Score = protocolEntity.getTeam2Score();

                if (groupInfo.getTeam() == protocolEntity.getTeam1()) {
                    updatePointsAndStatistic(team1Score, team2Score, groupInfo);
                } else if (groupInfo.getTeam() == protocolEntity.getTeam2()) {
                    updatePointsAndStatistic(team2Score, team1Score, groupInfo);
                }
            }
            orderedByPointList.add(groupInfo.toDTO());
        }


        orderedByPointList.sort((o1, o2) ->
                o2.getPoints().compareTo(o1.getPoints())
        );

        return orderedByPointList;
    }

    @Override
    public void incrementGoalCount(GroupEntity group, TeamEntity team) {
        repository.incrementGoalCount(group.getGroupId(), team.getTeamId());
    }

    @Override
    public void incrementGoalMissedCount(GroupEntity group, TeamEntity team) {
        repository.incrementGoalMissed(group.getGroupId(), team.getTeamId());
    }

    @Override
    public void decrementGoalCount(GroupEntity group, TeamEntity team) {
        repository.decrementGoalCount(group.getGroupId(), team.getTeamId());
    }

    @Override
    public void decrementGoalMissedCount(GroupEntity group, TeamEntity team) {
        repository.decrementGoalMissedCount(group.getGroupId(), team.getTeamId());
    }

    private void updatePointsAndStatistic(int foundTeam, int rivalTeam, GroupInfoEntity groupInfoEntity) {
        if (foundTeam > rivalTeam) {
            groupInfoEntity.setWinCount(groupInfoEntity.getWinCount() + 1);
            groupInfoEntity.setPoints(groupInfoEntity.getPoints() + 3);
        } else if (foundTeam == rivalTeam) {
            groupInfoEntity.setDrawCount(groupInfoEntity.getDrawCount() + 1);
            groupInfoEntity.setPoints(groupInfoEntity.getPoints() + 1);
        } else {
            groupInfoEntity.setLoseCount(groupInfoEntity.getLoseCount() + 1);
        }
    }
}
