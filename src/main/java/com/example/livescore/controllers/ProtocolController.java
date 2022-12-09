package com.example.livescore.controllers;


import com.example.livescore.service.ProtocolServiceImpl;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protocols")
@RequiredArgsConstructor
public class ProtocolController {

    private final ProtocolServiceImpl protocolService;

    @GetMapping("{id}")
    public ProtocolDTO getProtocol(@PathVariable Long id) {
        return protocolService.getIndividual(id);
    }

    @PostMapping
    public ProtocolDTO postProtocol(@RequestBody SaveProtocolDTO saveProtocolDTO) {
        return protocolService.postIndividual(saveProtocolDTO);
    }

    @PutMapping("{id}")
    public ProtocolDTO putProtocol(@RequestBody SaveProtocolDTO saveProtocolDTO, @PathVariable Long id) {
        return protocolService.putIndividual(id, saveProtocolDTO);
    }

    @DeleteMapping("{id}")
    public void deleteProtocol(@PathVariable Long id) {
        protocolService.deleteIndividual(id);
    }
}
