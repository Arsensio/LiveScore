package com.example.livescore.controllers.event.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.event.EventController;
import com.example.livescore.service.event.EventService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class DefaultEventController extends AbstractFootballController<EventService, EventDTO, SaveEventDTO, Long> implements EventController {

    public DefaultEventController(EventService service) {
        super(service);
    }

    @PostMapping("/save_goal")
    @Override
    public ResponseEntity<EventDTO> saveGoal(@RequestBody SaveGoalEventDTO saveGoalEventDTO) {
        return new ResponseEntity<>(service.save(saveGoalEventDTO), HttpStatus.OK);
    }
}
