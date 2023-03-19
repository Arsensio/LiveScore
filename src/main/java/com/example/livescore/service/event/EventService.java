package com.example.livescore.service.event;

import com.example.core.service.FootballService;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;

public interface EventService extends FootballService<EventDTO, SaveEventDTO,Long> {

    EventDTO save(SaveGoalEventDTO dto);
}
