package com.example.livescore.service.protocol;

import com.example.core.service.FootballService;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;

import java.util.List;


public interface ProtocolService extends FootballService<ProtocolEntity, ProtocolDTO, SaveProtocolDTO, Long> {

    ProtocolEntity saveAndFlush(ProtocolEntity protocol);

    List<ProtocolEntity> findAllByGameStateStarted();
}
