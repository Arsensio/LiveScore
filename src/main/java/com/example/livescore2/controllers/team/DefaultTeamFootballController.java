package com.example.livescore2.controllers.team;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore2.service.team.TeamFootballService;
import com.example.livescore2.web.teams.SaveTeamDTO;
import com.example.livescore2.web.teams.TeamDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class DefaultTeamFootballController
        extends AbstractFootballController<TeamFootballService, TeamDTO, SaveTeamDTO>
        implements TeamFootballController {

    public DefaultTeamFootballController(TeamFootballService service) {
        super(service);
    }
}
