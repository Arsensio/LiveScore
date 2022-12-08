package com.example.livescore.service;

import com.example.livescore.enums.EventNames;
import com.example.livescore.models.EventEntity;
import com.example.livescore.models.GameEntity;
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
public class EventServiceImpl implements MainService<SaveEventDTO, EventDTO>{

    private final EventRepository eventRepository;
    private final PlayerRepository playerRepository;

    private final ProtocolRepository protocolRepository;

    // в контексте одного матча, передавать матч айди
    @Override
    public List<EventDTO> getAll() {
        return eventRepository.findAll().stream().map(EventEntity::toDTO).collect(Collectors.toList());
    }

    @Override
    public EventDTO getIndividual(Long id) {
        return eventRepository.getReferenceById(id).toDTO();
    }

    @Override
    public EventDTO postIndividual(SaveEventDTO saveEventDTO) {
        return eventRepository.save(
                new EventEntity(
                        null,
                        playerRepository.getReferenceById(saveEventDTO.getPlayerId()),
                        saveEventDTO.getMinute(),
                        EventNames.getEventById(saveEventDTO.getEventEnumId()).toString()
                )
        ).toDTO();
    }

    @Override
    public EventDTO putIndividual(Long id, SaveEventDTO saveEventDTO) {
//        return eventRepository.save(
//                new EventEntity(
//                        id,
//                        playerRepository.getReferenceById(saveEventDTO.getPlayerId()),
//                        saveEventDTO.getMinute(),
//                        saveEventDTO.getEventName()
//                )
//        ).toDTO();
        return null;
    }

    @Override
    public void deleteIndividual(Long id) {
        eventRepository.deleteById(id);
    }
}
