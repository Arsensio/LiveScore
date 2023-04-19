package com.example.livescore.controllers.group_info;

import com.example.core.controller.FootballController;
import com.example.livescore.web.group_info.FinishStageDTO;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.GroupInfoListDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupInfoController extends FootballController<GroupInfoDTO, SaveGroupInfoDTO, Long> {

    ResponseEntity<List<GroupInfoDTO>> createDrawInCup(List<SaveGroupInfoDTO> list);

    ResponseEntity<List<GroupInfoListDTO>> findAllGroupsSortedByPoints(long tournamentId);

    ResponseEntity<List<GroupInfoListDTO>> findGroupSortedByPoint(long tournamentId, long groupId);

    ResponseEntity<List<TeamDTO>> finishGroupStage(long tournamentId);

    ResponseEntity<List<TeamDTO>> finishStage(long tournamentId, FinishStageDTO finishStageDTO);

}
