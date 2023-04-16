package com.example.livescore.controllers.event_info;

import com.example.core.controller.FootballController;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;

public interface EventInfoController extends FootballController<AssistDTO, SaveAssistsDTO, Long> {
}
