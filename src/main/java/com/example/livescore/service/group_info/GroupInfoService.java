package com.example.livescore.service.group_info;

import com.example.core.service.FootballService;
import com.example.livescore.models.GroupEntity;
import com.example.livescore.models.GroupInfoEntity;
import com.example.livescore.models.TeamEntity;
import com.example.livescore.models.TournamentEntity;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;

import java.util.List;

public interface GroupInfoService extends FootballService<GroupInfoDTO, SaveGroupInfoDTO, Long> {

    GroupInfoEntity saveAfterDraw(GroupEntity group, TournamentEntity tournament, TeamEntity team);

    GroupInfoEntity findEntityByGroupAndTeamId(TournamentEntity tournament, GroupEntity group, TeamEntity team);

    GroupInfoEntity saveAndFlash(GroupInfoEntity groupInfoEntity);

    List<GroupInfoDTO> findTeamsSortedByPoints(long tournament, long group);

    void incrementGoalCount(GroupEntity group, TeamEntity team);

    void incrementGoalMissedCount(GroupEntity group, TeamEntity team);

    void decrementGoalCount(GroupEntity group, TeamEntity team);

    void decrementGoalMissedCount(GroupEntity group, TeamEntity team);

    void incrementGameCount(GroupEntity group, TeamEntity team);

    List<GroupInfoEntity> findAllByTournamentIdOrderByWinCount(Long tournament, Long group);

    List<GroupInfoDTO> createDrawInCup(List<SaveGroupInfoDTO> list);
}
