package com.example.livescore.service.event;

import com.example.core.service.FootballService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;

public interface EventService extends FootballService<EventDTO, SaveEventDTO,Long> {
}
