package com.example.livescore.controllers.goal_info.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.goal_info.GoalInfoController;
import com.example.livescore.service.goal_info.GoalInfoService;
import com.example.livescore.web.assists.GoalInfoDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goal_info")
public class DefaultGoalInfoController
        extends AbstractFootballController<GoalInfoService, GoalInfoDTO, SaveAssistsDTO, Long>
        implements GoalInfoController {

    public DefaultGoalInfoController(GoalInfoService service) {
        super(service);
    }
}
