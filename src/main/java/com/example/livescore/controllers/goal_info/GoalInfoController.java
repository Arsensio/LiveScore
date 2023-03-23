package com.example.livescore.controllers.goal_info;

import com.example.core.controller.FootballController;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;

public interface GoalInfoController extends FootballController<AssistDTO, SaveAssistsDTO, Long> {
}
