package com.example.livescore.service.group_info.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.*;
import com.example.livescore.repository.GroupInfoRepository;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.service.team_statistics.TeamStatisticsService;
import com.example.livescore.service.tournament.TournamentService;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.GroupInfoListDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.livescore.enums.GroupStatusEnum.*;


@Service
public class DefaultGroupInfoService
        extends AbstractFootballService<GroupInfoEntity, GroupInfoDTO, SaveGroupInfoDTO, Long, GroupInfoRepository>
        implements GroupInfoService {

    private final TournamentService tournamentService;
    private final TeamFootballService teamFootballService;
    private final TeamStatisticsService teamStatisticsService;
    private final GroupService groupService;
    private final ProtocolService protocolService;

    public DefaultGroupInfoService(GroupInfoRepository repository, TournamentService tournamentService, TeamFootballService teamFootballService, TeamStatisticsService teamStatisticsService, GroupService groupService, ProtocolService protocolService) {
        super(repository);
        this.tournamentService = tournamentService;
        this.teamFootballService = teamFootballService;
        this.teamStatisticsService = teamStatisticsService;
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
    public List<TeamDTO> finishGroupStage(long tournamentId) {
        TournamentEntity tournament = tournamentService.findEntityById(tournamentId);
        List<GroupEntity> groupEntities = groupService.findAllGroupInGroupStageByTournamentId(tournamentId);
        List<ProtocolEntity> allByGameStateStarted = protocolService.findAllByGameStateStarted();


        List<List<GroupInfoEntity>> allSortedGroups = new ArrayList<>();

        for (GroupEntity g : groupEntities) {
            List<GroupInfoEntity> sortedGroup = repository.findAllByTournamentIdAndGroupIdOrderByWinCount(tournamentId, g.getGroupId());
            List<GroupInfoEntity> sortedList = new ArrayList<>();

            for (GroupInfoEntity gi : sortedGroup) {
                updateIfLiveMatch(allByGameStateStarted, sortedList, gi);
                gi.setStatus(FINISHED.toString());
                repository.saveAndFlush(gi);
            }
            sortedList.sort(sortGroupEntityAlgorithm());
            allSortedGroups.add(sortedList);

        }

        List<TeamDTO> nextStageTeams = new ArrayList<>();

        for (List<GroupInfoEntity> list : allSortedGroups) {
            for (int i = 0; i < 2; i++) {
                GroupInfoEntity groupInfoEntity = list.get(i);
                TeamEntity nextStageTeam = groupInfoEntity.getTeam();
                GroupEntity nextStage = groupService.findNextStage(groupInfoEntity.getGroup());

                GroupInfoEntity newGroupInfo = GroupInfoEntity.builder()
                        .tournamentLogo(nextStage.getTournament().getTournamentLogo())
                        .groupName(nextStage.getGroupName())
                        .teamName(nextStageTeam.getTeamName())
                        .teamLogo(nextStageTeam.getTeamLogo())
                        .gamePlayed(groupInfoEntity.getGamePlayed())
                        .winCount(groupInfoEntity.getWinCount())
                        .drawCount(groupInfoEntity.getDrawCount())
                        .loseCount(groupInfoEntity.getLoseCount())
                        .goalCount(groupInfoEntity.getGoalCount())
                        .goalMissed(groupInfoEntity.getGoalMissed())
                        .status(IN_PROGRESS.toString())
                        .group(nextStage)
                        .team(nextStageTeam)
                        .tournament(tournament)
                        .points(0)
                        .build();

                repository.saveAndFlush(newGroupInfo);
                nextStageTeams.add(nextStageTeam.toDTO());
            }
        }


        return nextStageTeams;
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
                updateIfLiveMatchDTO(allByGameStateStarted, orderedByPointList, groupInfo);
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
            updateIfLiveMatchDTO(allByGameStateStarted, orderedByPointList, groupInfo);
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

    private void updateIfLiveMatchDTO(List<ProtocolEntity> allByGameStateStarted, List<GroupInfoDTO> orderedByPointList, GroupInfoEntity groupInfo) {
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

    private void updateIfLiveMatch(List<ProtocolEntity> allByGameStateStarted, List<GroupInfoEntity> orderedByPointList, GroupInfoEntity groupInfo) {
        for (ProtocolEntity protocolEntity : allByGameStateStarted) {
            int team1Score = protocolEntity.getTeam1Score();
            int team2Score = protocolEntity.getTeam2Score();

            if (groupInfo.getTeam() == protocolEntity.getTeam1()) {
                updatePointsAndStatistic(team1Score, team2Score, groupInfo);
            } else if (groupInfo.getTeam() == protocolEntity.getTeam2()) {
                updatePointsAndStatistic(team2Score, team1Score, groupInfo);
            }
        }

        orderedByPointList.add(groupInfo);
    }

    private static Comparator<GroupInfoDTO> sortGroupAlgorithm() {
        return Comparator
                .comparingInt(GroupInfoDTO::getPoints)
                .reversed()
                .thenComparing((p1, p2) ->
                        Integer.compare(p1.getGoalCount() - p1.getGoalMissed(), p2.getGoalCount() + p2.getGoalMissed()));
    }

    private static Comparator<GroupInfoEntity> sortGroupEntityAlgorithm() {
        return Comparator
                .comparingInt(GroupInfoEntity::getPoints)
                .reversed()
                .thenComparing((p1, p2) ->
                        Integer.compare(p1.getGoalCount() - p1.getGoalMissed(), p2.getGoalCount() + p2.getGoalMissed()));
    }
}
