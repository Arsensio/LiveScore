package com.example.livescore.service.goal_info.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.enums.EventNames;
import com.example.livescore.models.EventEntity;
import com.example.livescore.models.GoalInfoEntity;
import com.example.livescore.models.PlayerEntity;
import com.example.livescore.repository.GoalInfoRepository;
import com.example.livescore.service.goal_info.GoalInfoService;
import com.example.livescore.web.assists.GoalInfoDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultGoalInfoService
        extends AbstractFootballService<GoalInfoEntity, GoalInfoDTO, SaveAssistsDTO, Long, GoalInfoRepository>
        implements GoalInfoService {

    public DefaultGoalInfoService(GoalInfoRepository repository) {
        super(repository);
    }

    @Override
    public GoalInfoEntity saveAssist(PlayerEntity assistPlayer, EventEntity event) {
        return repository.save(getGoalInfoEntity(assistPlayer, event, 2));
    }

    @Override
    public GoalInfoEntity savePenalty(PlayerEntity assistPlayer, EventEntity event) {
        return repository.save(getGoalInfoEntity(null, event, 5));
    }

    @Override
    public GoalInfoEntity findEntityById(Long id) {
        Optional<GoalInfoEntity> goalInfoEntity = repository.findById(id);
        if (goalInfoEntity.isEmpty()) {
            throw ResourceNotFoundException.build(id, "GoalInfoEntity");
        } else {
            return goalInfoEntity.get();
        }
    }

    private GoalInfoEntity getGoalInfoEntity(PlayerEntity assistPlayer, EventEntity event, long eventId) {
        return new GoalInfoEntity(assistPlayer, EventNames.getEventNameById(eventId), event);
    }

}
