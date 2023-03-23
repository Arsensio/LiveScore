package com.example.livescore.controllers.group.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.group.GroupController;
import com.example.livescore.service.group.GroupService;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/group")
public class DefaultGroupController
        extends AbstractFootballController<GroupService, GroupDTO, SaveGroupDTO, Long>
        implements GroupController {

    public DefaultGroupController(GroupService service) {
        super(service);
    }

    @Override
    @GetMapping("/group")
    public ResponseEntity<List<GroupDTO>> findAllByTournamentId(@PathParam("tournament_id") long tournamentId) {
        return new ResponseEntity<>(service.findAllByTournamentId(tournamentId), HttpStatus.OK);
    }
}
