package com.example.livescore.controllers.group_info;

import com.example.core.controller.FootballController;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupInfoController extends FootballController<GroupInfoDTO, SaveGroupInfoDTO, Long> {

    ResponseEntity<List<GroupInfoDTO>> createDrawInCup(List<SaveGroupInfoDTO> list);

    ResponseEntity<List<GroupInfoDTO>> findAllSortedByPoints(long tournament_id, long group_id);

}
