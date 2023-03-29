package com.example.livescore.service.protocol.impl;

import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.service.AbstractFootballService;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.repository.ProtocolRepository;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProtocolService
        extends AbstractFootballService<ProtocolEntity, ProtocolDTO, SaveProtocolDTO, Long, ProtocolRepository>
        implements ProtocolService {

    public DefaultProtocolService(ProtocolRepository repository) {
        super(repository);
    }

    @Override
    public ProtocolEntity findEntityById(long id) {
        Optional<ProtocolEntity> protocol = repository.findById(id);
        if (protocol.isEmpty()) {
            throw ResourceNotFoundException.build(id, "ProtocolEntity");
        } else {
            return protocol.get();
        }
    }

    @Override
    public ProtocolEntity saveAndFlush(ProtocolEntity protocol) {
        return repository.saveAndFlush(protocol);
    }

    @Override
    public List<ProtocolEntity> findAllByGameStateStarted() {
        return repository.findAllByGameStateStarted();
    }
}
