package com.example.livescore.controllers.event.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.event.EventController;
import com.example.livescore.service.event.EventService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class DefaultEventController extends AbstractFootballController<EventService, EventDTO, SaveEventDTO,Long> implements EventController {

    public DefaultEventController(EventService service) {
        super(service);
    }
}
