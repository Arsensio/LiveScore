package com.example.livescore.service.goal_info.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GoalInfoEntity;
import com.example.livescore.repository.GoalInfoRepository;
import com.example.livescore.service.goal_info.GoalInfoService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultGoalInfoService
        extends AbstractFootballService<GoalInfoEntity, AssistDTO, SaveAssistsDTO, Long, GoalInfoRepository>
        implements GoalInfoService {

    public DefaultGoalInfoService(GoalInfoRepository repository) {
        super(repository);
    }
}
