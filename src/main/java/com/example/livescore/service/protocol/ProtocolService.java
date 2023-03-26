package com.example.livescore.service.protocol;

import com.example.core.service.FootballService;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;


public interface ProtocolService extends FootballService<ProtocolDTO, SaveProtocolDTO, Long> {

    ProtocolEntity findEntityById(long id);

    ProtocolEntity saveAndFlush(ProtocolEntity protocol);
}
