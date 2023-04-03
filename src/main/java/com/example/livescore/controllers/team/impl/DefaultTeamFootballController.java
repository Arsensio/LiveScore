package com.example.livescore.controllers.team.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.team.TeamFootballController;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import com.example.livescore.web.teams.TeamWithPlayersDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/team")
public class DefaultTeamFootballController extends AbstractFootballController<TeamFootballService, TeamDTO,
        SaveTeamDTO, Long>
        implements TeamFootballController {

    public DefaultTeamFootballController(TeamFootballService service) {
        super(service);
    }

    @Override
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<TeamDTO>> findAllTeamByGroupId(@PathVariable long groupId) {
        return new ResponseEntity<>(service.findAllTeamByGroupId(groupId), OK);
    }

    @Override
    @GetMapping("/teamAndItsPlayers")
    public ResponseEntity<List<TeamWithPlayersDto>> findAllTeamsAndItsPlayers() {
        return new ResponseEntity<>(service.findAllTeamsAndItsPlayers(), OK);
    }
}
