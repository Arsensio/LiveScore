package com.example.livescore.service;

import com.example.livescore.enums.EventNames;
import com.example.livescore.models.EventEntity;
import com.example.livescore.models.ProtocolEntity;
import com.example.livescore.store.EventRepository;
import com.example.livescore.store.PlayerRepository;
import com.example.livescore.store.ProtocolRepository;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;
    private final ProtocolRepository protocolRepository;

    @Override
    public List<EventDTO> getAll(Long protocolId) {
        return eventRepository.getEventEntitiesByProtocolId(protocolId).stream().map(EventEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public EventDTO postIndividual(SaveEventDTO saveEventDTO) {
        if (saveEventDTO.getEventEnumId() == 1) setScore(saveEventDTO);
        return eventRepository.save(
                new EventEntity(
                        null,
                        protocolRepository.getReferenceById(saveEventDTO.getProtocolId()),
                        EventNames.getEventById(saveEventDTO.getEventEnumId()).toString(), // мы посылаем цифру, потом конвертируем его в стринг, зачем?, исправить потом
                        playerRepository.getReferenceById(saveEventDTO.getPlayerId()),
                        saveEventDTO.getMinute()
                )
        ).toDTO();
    }

    @Override
    public EventDTO putIndividual(Long id, SaveEventDTO saveEventDTO) {
        if (saveEventDTO.getEventEnumId() == 1) setScore(saveEventDTO);

        return eventRepository.save(
                new EventEntity(
                        id,
                        protocolRepository.getReferenceById(saveEventDTO.getProtocolId()),
                        EventNames.getEventById(saveEventDTO.getEventEnumId()).toString(),
                        playerRepository.getReferenceById(saveEventDTO.getPlayerId()),
                        saveEventDTO.getMinute()
                )
        ).toDTO();
    }

    @Override
    public void deleteIndividual(Long id) {
        eventRepository.deleteById(id);
    }

    private void setScore(SaveEventDTO saveEventDTO) {
        ProtocolEntity pE = protocolRepository.getReferenceById(saveEventDTO.getProtocolId());
        boolean first = playerRepository.getReferenceById(saveEventDTO.getPlayerId()).getTeam().equals(pE.getTeam1());
        pE.incrementTeamScore(first);
    }
}
