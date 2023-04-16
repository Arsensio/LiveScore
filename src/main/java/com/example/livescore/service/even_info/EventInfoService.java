package com.example.livescore.service.even_info;

import com.example.core.service.FootballService;
import com.example.livescore.models.EventEntity;
import com.example.livescore.models.EventInfoEntity;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;

import java.util.List;

public interface EventInfoService extends FootballService<AssistDTO, SaveAssistsDTO, Long> {

    EventInfoEntity saveAssist(PlayerEntity assistPlayer, EventEntity event);

    EventInfoEntity savePenalty(PlayerEntity assistPlayer, EventEntity event);

    EventInfoEntity saveEventInfo(EventInfoEntity eventInfoEntity);

    List<EventInfoEntity> findAllByEvent(EventEntity event);
}
