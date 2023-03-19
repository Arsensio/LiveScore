package com.example.livescore.service.assists.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.GoalInfoEntity;
import com.example.livescore.repository.GoalInfoRepository;
import com.example.livescore.service.assists.AssistService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultAssistService
        extends AbstractFootballService<GoalInfoEntity, AssistDTO, SaveAssistsDTO, Long, GoalInfoRepository>
        implements AssistService {

    public DefaultAssistService(GoalInfoRepository repository) {
        super(repository);
    }
}
