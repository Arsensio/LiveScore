package com.example.livescore.controllers.group;

import com.example.core.controller.FootballController;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupController extends FootballController<GroupDTO, SaveGroupDTO, Long> {

    ResponseEntity<List<GroupDTO>> findAllByTournamentId(long tournamentId);

    ResponseEntity<List<GroupDTO>> findGroupTabsByTournament(long tournamentId);

    ResponseEntity<List<GroupDTO>> findAllGroupStageByTournamentId(long tournamentId);
}
