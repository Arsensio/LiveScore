package com.example.livescore.service.assists.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.AssistEntity;
import com.example.livescore.repository.AssistRepository;
import com.example.livescore.service.assists.AssistService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultAssistService
        extends AbstractFootballService<AssistEntity, AssistDTO, SaveAssistsDTO, Long, AssistRepository>
        implements AssistService {

    public DefaultAssistService(AssistRepository repository) {
        super(repository);
    }
}
