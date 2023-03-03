package com.example.livescore.service.event.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.EventEntity;
import com.example.livescore.repository.EventRepository;
import com.example.livescore.service.event.EventService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultEventService
        extends AbstractFootballService<EventEntity, EventDTO, SaveEventDTO, Long, EventRepository>
        implements EventService {

    public DefaultEventService(EventRepository repository) {
        super(repository);
    }
}
