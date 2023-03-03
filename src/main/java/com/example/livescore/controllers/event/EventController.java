package com.example.livescore.controllers.event;

import com.example.core.controller.FootballController;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;

public interface EventController extends FootballController<EventDTO, SaveEventDTO,Long> {
}
