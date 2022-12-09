package com.example.livescore.controllers;

import com.example.livescore.service.EventServiceImpl;
import com.example.livescore.web.events.EventDTO;
import com.example.livescore.web.events.SaveEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventServiceImpl eventService;

    @GetMapping("/{protocolId}")
    public List<EventDTO> getEventsOfMatch(@PathVariable Long protocolId) {
        return eventService.getAll(protocolId);
    }

    @PostMapping
    public EventDTO postEvent(@RequestBody SaveEventDTO saveEventDTO) {
        return eventService.postIndividual(saveEventDTO);
    }

    @PutMapping("/{id}")
    public EventDTO putEvent(@PathVariable Long id, @RequestBody SaveEventDTO saveEventDTO) {
        return eventService.putIndividual(id, saveEventDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteIndividual(id);
    }
}
