package com.example.livescore.service.group_info;

import com.example.core.service.FootballService;
import com.example.livescore.models.*;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;

import java.util.List;

public interface GroupInfoService extends FootballService<GroupInfoDTO, SaveGroupInfoDTO, Long> {

    GroupInfoEntity saveAfterDraw(GroupEntity group, TournamentEntity tournament, TeamEntity team);

    //    void incrementGoalCount(GroupEntity group, TournamentEntity tournament, TeamEntity team);
//
//    void incrementGoalMissedCount(GroupEntity group, TournamentEntity tournament, TeamEntity team);
//
//    void decrementGoalCount(GroupEntity group, TournamentEntity tournament, TeamEntity team);
//
//    void decrementGoalMissedCount(GroupEntity group, TournamentEntity tournament, TeamEntity team);
//
    void incrementGameCount(GroupEntity group, TeamEntity team);

    List<GroupInfoDTO> createDrawInCup(List<SaveGroupInfoDTO> list);
}
