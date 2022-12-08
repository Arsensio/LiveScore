package com.example.livescore.service;


import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.store.ProtocolRepository;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Protocol;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements MainService<SaveProtocolDTO, ProtocolDTO> {

    private final ProtocolRepository protocolRepository;

    @Override
    public List<ProtocolDTO> getAll() {
        return protocolRepository.findAll().stream().map(ProtocolEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProtocolDTO getIndividual(Long id) {
        return protocolRepository.getReferenceById(id).toDTO();
    }

    @Override
    public ProtocolDTO postIndividual(SaveProtocolDTO saveProtocolDTO) {
        return null;
    }

    @Override
    public ProtocolDTO putIndividual(Long id, SaveProtocolDTO saveProtocolDTO) {
        return null;
    }

    @Override
    public void deleteIndividual(Long id) {

    }
}
