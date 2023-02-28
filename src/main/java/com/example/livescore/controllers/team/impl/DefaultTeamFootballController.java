package com.example.livescore.controllers.team.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.team.TeamFootballController;
import com.example.livescore.service.team.TeamFootballService;
import com.example.livescore.web.teams.SaveTeamDTO;
import com.example.livescore.web.teams.TeamDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class DefaultTeamFootballController
        extends AbstractFootballController<TeamFootballService, TeamDTO, SaveTeamDTO, Long>
        implements TeamFootballController {

    public DefaultTeamFootballController(TeamFootballService service) {
        super(service);
    }
}
