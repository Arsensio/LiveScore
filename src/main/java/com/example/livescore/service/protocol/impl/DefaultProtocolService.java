package com.example.livescore.service.protocol.impl;

import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.repository.ProtocolRepository;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;
import org.springframework.stereotype.Service;

@Service
public class DefaultProtocolService extends AbstractFootballService<ProtocolEntity, ProtocolDTO, SaveProtocolDTO,
        Long, ProtocolRepository>
        implements ProtocolService {

    public DefaultProtocolService(ProtocolRepository repository) {
        super(repository);
    }
}
