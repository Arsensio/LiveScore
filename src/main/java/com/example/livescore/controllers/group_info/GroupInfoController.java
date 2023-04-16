package com.example.livescore.controllers.group_info;

import com.example.core.controller.FootballController;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.GroupInfoListDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupInfoController extends FootballController<GroupInfoDTO, SaveGroupInfoDTO, Long> {

    ResponseEntity<List<GroupInfoDTO>> createDrawInCup(List<SaveGroupInfoDTO> list);

    ResponseEntity<List<GroupInfoListDTO>> findAllGroupsSortedByPoints(long tournamentId);

    ResponseEntity<List<GroupInfoListDTO>> findGroupSortedByPoint(long tournamentId, long groupId);

}
