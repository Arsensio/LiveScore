package com.example.livescore.controllers.event;

import com.example.core.controller.FootballController;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;
import org.springframework.http.ResponseEntity;

public interface EventController extends FootballController<EventDTO, SaveEventDTO, Long> {

    ResponseEntity<EventDTO> saveGoal(SaveGoalEventDTO saveGoalEventDTO);

    ResponseEntity<EventDTO> updateGoal(Long id, SaveGoalEventDTO saveGoalEventDTO);

    ResponseEntity<EventDTO> savePenalty(SaveEventDTO saveEventDTO);

    ResponseEntity<EventDTO> updatePenalty(Long id, SaveEventDTO saveEventDTO);
}
