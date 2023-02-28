package com.example.livescore.service;


import com.example.livescore.models.GameEntity;
import com.example.livescore.models.PlayerStatisticsEntity;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.store.GameRepository;
import com.example.livescore.store.ProtocolRepository;
import com.example.livescore.store.TeamRepository;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {
    private final ProtocolRepository protocolRepository;
    private final TeamRepository teamRepository;

    private final GameRepository gameRepository;

    @Override
    public ProtocolDTO getIndividual(Long id) {
        return protocolRepository.getReferenceById(id).toDTO();
    }

    @Override
    public ProtocolDTO postIndividual(SaveProtocolDTO saveProtocolDTO) {
        return protocolRepository.save(
                new ProtocolEntity(
                        null,
                        gameRepository.getReferenceById(saveProtocolDTO.getGameId()),
                        teamRepository.getReferenceById(saveProtocolDTO.getTeam1Id()),
                        teamRepository.getReferenceById(saveProtocolDTO.getTeam2Id()),
                        saveProtocolDTO.getDateAndTime(),
                        0,
                        0
                )
        ).toDTO();
    }

    @Override
    public ProtocolDTO putIndividual(Long id, SaveProtocolDTO saveProtocolDTO) {
        return protocolRepository.save(
                new ProtocolEntity(
                        id,
                        gameRepository.getReferenceById(saveProtocolDTO.getGameId()),
                        teamRepository.getReferenceById(saveProtocolDTO.getTeam1Id()),
                        teamRepository.getReferenceById(saveProtocolDTO.getTeam2Id()),
                        saveProtocolDTO.getDateAndTime(),
                        0,
                        0
                )
        ).toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        protocolRepository.deleteById(id);
    }
}
