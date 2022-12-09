package com.example.livescore.service;

import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;

import java.util.List;

public interface EventService {

    List<EventDTO> getAll(Long protocolId);

    EventDTO postIndividual(SaveEventDTO t);

    EventDTO putIndividual(Long id, SaveEventDTO t);

    void deleteIndividual(Long id);

}
