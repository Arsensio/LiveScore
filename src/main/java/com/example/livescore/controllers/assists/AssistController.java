package com.example.livescore.controllers.assists;

import com.example.core.controller.FootballController;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;

public interface AssistController extends FootballController<AssistDTO, SaveAssistsDTO, Long> {
}
