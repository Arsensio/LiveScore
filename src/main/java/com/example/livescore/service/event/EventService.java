package com.example.livescore.service.event;

import com.example.core.service.FootballService;
import com.example.livescore.models.EventEntity;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import com.example.livescore.web.events.SaveGoalEventDTO;

public interface EventService extends FootballService<EventEntity, EventDTO, SaveEventDTO, Long> {

    EventDTO saveGoal(SaveGoalEventDTO dto);

    EventDTO updateGoal(Long id, SaveGoalEventDTO dto);
}
