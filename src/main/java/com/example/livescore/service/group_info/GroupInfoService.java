package com.example.livescore.service.group_info;

import com.example.core.service.FootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.GroupInfoEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.web.group_info.*;
import com.example.livescore.web.teams.TeamDTO;

import java.util.List;

public interface GroupInfoService extends FootballService<GroupInfoEntity, GroupInfoDTO, SaveGroupInfoDTO, Long> {

    GroupInfoEntity saveAfterDraw(GroupEntity group, TournamentEntity tournament, TeamEntity team);

    GroupInfoEntity findEntityByTournamentAndGroupAndTeam(TournamentEntity tournament, GroupEntity group, TeamEntity team);

    GroupInfoEntity saveAndFlash(GroupInfoEntity groupInfoEntity);

    List<GroupInfoListDTO> findAllTeamsFromTournamentSortedByPoints(long tournament);

    List<GroupInfoListDTO> findGroupSortedByPoints(long tournament, long group);


    List<GroupInfoDTO> createDrawInCup(List<SaveGroupInfoDTO> list);

    void incrementGoalCount(GroupEntity group, TeamEntity team);

    void incrementGoalMissedCount(GroupEntity group, TeamEntity team);

    void decrementGoalCount(GroupEntity group, TeamEntity team);

    void decrementGoalMissedCount(GroupEntity group, TeamEntity team);

    void incrementGameCount(GroupEntity group, TeamEntity team);

    List<TeamDTO> finishGroupStage(long tournamentId);

    List<TeamDTO> finishStage(long tournamentId, FinishStageDTO finishStageDTO);

    List<AfterDrawDTO> getTablesAfterDraw(long tournamentId);
}
