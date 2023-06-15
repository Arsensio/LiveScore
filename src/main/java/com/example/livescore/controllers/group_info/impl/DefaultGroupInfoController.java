package com.example.livescore.controllers.group_info.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.group_info.GroupInfoController;
import com.example.livescore.models.GroupInfoEntity;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.web.group_info.*;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/group_info")
public class DefaultGroupInfoController
        extends AbstractFootballController<GroupInfoEntity, GroupInfoService, GroupInfoDTO, SaveGroupInfoDTO, Long>
        implements GroupInfoController {

    public DefaultGroupInfoController(GroupInfoService service) {
        super(service);
    }

    @Override
    @PostMapping("/create_draw_in_cup")
    public ResponseEntity<List<GroupInfoDTO>> createDrawInCup(@RequestBody List<SaveGroupInfoDTO> list) {
        return new ResponseEntity<>(service.createDrawInCup(list), OK);
    }

    @Override
    @GetMapping("/all_group/points")
    public ResponseEntity<List<GroupInfoListDTO>> findAllGroupsSortedByPoints(long tournamentId) {
        return new ResponseEntity<>(service.findAllTeamsFromTournamentSortedByPoints(tournamentId), OK);
    }

    @Override
    @GetMapping("/group/points")
    public ResponseEntity<List<GroupInfoListDTO>> findGroupSortedByPoint(long tournamentId, long groupId) {
        return new ResponseEntity<>(service.findGroupSortedByPoints(tournamentId, groupId), OK);
    }

    @Override
    @PostMapping("/finish_group_stage/{tournament_id}")
    public ResponseEntity<List<TeamDTO>> finishGroupStage(@PathVariable("tournament_id") long tournamentId) {
        return new ResponseEntity<>(service.finishGroupStage(tournamentId), OK);
    }

    @Override
    @PostMapping("/finish_stage/{tournament_id}")
    public ResponseEntity<List<TeamDTO>> finishStage(@PathVariable("tournament_id") long tournamentId, @RequestBody FinishStageDTO finishStageDTO) {
        return new ResponseEntity<>(service.finishStage(tournamentId, finishStageDTO), OK);
    }

    @Override
    @PostMapping("/finish_league/{tournament_id}")
    public ResponseEntity<TeamDTO> finishLeague(@PathVariable("tournament_id") long tournamentId) {
        return new ResponseEntity<>(service.finishLeague(tournamentId), OK);
    }


    @Override
    @GetMapping("/tables_after_draw/{tournamentId}")
    public ResponseEntity<List<AfterDrawDTO>> getTablesAfterDraw(@PathVariable("tournamentId") long tournamentId) {
        return new ResponseEntity<>(service.getTablesAfterDraw(tournamentId), OK);
    }
}
