package com.example.livescore.service.even_info.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventEnum;
import com.example.livescore.models.EventEntity;
import com.example.livescore.models.EventInfoEntity;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.repository.EventInfoRepository;
import com.example.livescore.service.even_info.EventInfoService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultEventInfoService
        extends AbstractFootballService<EventInfoEntity, AssistDTO, SaveAssistsDTO, Long, EventInfoRepository>
        implements EventInfoService {

    public DefaultEventInfoService(EventInfoRepository repository) {
        super(repository);
    }

    @Override
    public EventInfoEntity saveAssist(PlayerEntity assistPlayer, EventEntity event) {
        return repository.save(getGoalInfoEntity(assistPlayer, event, 2));
    }

    @Override
    public EventInfoEntity savePenalty(PlayerEntity assistPlayer, EventEntity event) {
        return repository.save(getGoalInfoEntity(null, event, 5));
    }

    @Override
    public EventInfoEntity saveEventInfo(EventInfoEntity eventInfoEntity) {
        return repository.save(eventInfoEntity);
    }

    @Override
    public List<EventInfoEntity> findAllByEvent(EventEntity event) {
        return repository.findAllByEvent(event);
    }

    private EventInfoEntity getGoalInfoEntity(PlayerEntity assistPlayer, EventEntity event, long eventId) {
        return new EventInfoEntity(assistPlayer, EventEnum.getEventNameById(eventId), event);
    }

}
