package com.example.livescore.service.group_info.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.*;
import com.example.livescore.repository.GroupInfoRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.GroupInfoListDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    public List<GroupInfoListDTO> findAllTeamsFromTournamentSortedByPoints(long tournamentId) {
        List<GroupEntity> groupEntities = groupService.findAllGroupInGroupStageByTournamentId(tournamentId);
        List<ProtocolEntity> allByGameStateStarted = protocolService.findAllByGameStateStarted();
        List<GroupInfoListDTO> returnGroupList = new ArrayList<>();

        for (GroupEntity group : groupEntities) {
            GroupInfoListDTO groupInfoListDTO = new GroupInfoListDTO(group);
            List<GroupInfoDTO> orderedByPointList = new ArrayList<>();

            List<GroupInfoEntity> allGroupInfoByTournamentAndGroup = repository.findAllByTournamentIdAndGroupIdOrderByWinCount(tournamentId, group.getGroupId());
            for (GroupInfoEntity groupInfo : allGroupInfoByTournamentAndGroup) {
                updateIfLiveMatch(allByGameStateStarted, orderedByPointList, groupInfo);
            }

            orderedByPointList.sort(sortGroupAlgorithm());

            groupInfoListDTO.setSortedByPointTeams(orderedByPointList);
            returnGroupList.add(groupInfoListDTO);
        }


        return returnGroupList;
    }

    @Override
    public List<GroupInfoListDTO> findGroupSortedByPoints(long tournament, long group) {
        List<GroupInfoListDTO> returnGroupList = new ArrayList<>();

        List<ProtocolEntity> allByGameStateStarted = protocolService.findAllByGameStateStarted();
        List<GroupInfoDTO> orderedByPointList = new ArrayList<>();

        List<GroupInfoEntity> allByTournamentIdAndGroupIdOrderByWinCount = repository.findAllByTournamentIdAndGroupIdOrderByWinCount(tournament, group);
        GroupInfoListDTO groupInfoListDTO = new GroupInfoListDTO(allByTournamentIdAndGroupIdOrderByWinCount.get(0).getGroup());
        for (GroupInfoEntity groupInfo : allByTournamentIdAndGroupIdOrderByWinCount) {
            updateIfLiveMatch(allByGameStateStarted, orderedByPointList, groupInfo);
        }

        orderedByPointList.sort(sortGroupAlgorithm());
        groupInfoListDTO.setSortedByPointTeams(orderedByPointList);
        returnGroupList.add(groupInfoListDTO);

        return returnGroupList;
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

    private void updateIfLiveMatch(List<ProtocolEntity> allByGameStateStarted, List<GroupInfoDTO> orderedByPointList, GroupInfoEntity groupInfo) {
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

    private static Comparator<GroupInfoDTO> sortGroupAlgorithm() {
        return Comparator
                .comparingInt(GroupInfoDTO::getPoints)
                .reversed()
                .thenComparing((p1, p2) ->
                        Integer.compare(p1.getGoalCount() - p1.getGoalMissed(), p2.getGoalCount() + p2.getGoalMissed()));
    }
}
