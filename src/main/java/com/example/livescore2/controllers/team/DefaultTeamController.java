package com.example.livescore2.controllers.team;

import com.example.core.controller.AbstractController;
import com.example.livescore2.service.TeamService;
import com.example.livescore2.web.teams.SaveTeamDTO;
import com.example.livescore2.web.teams.TeamDTO;

public class DefaultTeamController
        extends AbstractController<TeamService, TeamDTO, SaveTeamDTO>
        implements TeamController {

    public DefaultTeamController(TeamService service) {
        super(service);
    }
}
