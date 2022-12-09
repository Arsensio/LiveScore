package com.example.livescore.service;

import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;

public interface ProtocolService {

    ProtocolDTO getIndividual(Long id);

    ProtocolDTO postIndividual(SaveProtocolDTO t);

    ProtocolDTO putIndividual(Long id, SaveProtocolDTO t);

    void deleteIndividual(Long id);

}
