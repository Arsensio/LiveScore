package com.example.livescore.controllers.protocol;

import com.example.core.controller.FootballController;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;

public interface ProtocolController extends FootballController<ProtocolDTO, SaveProtocolDTO,Long> {
}
