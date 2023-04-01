package com.example.livescore.controllers.protocol.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.protocol.ProtocolController;
import com.example.livescore.service.protocol.ProtocolService;
import com.example.livescore.web.protocols.ProtocolDTO;
import com.example.livescore.web.protocols.SaveProtocolDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protocol")
public class DefaultProtocolController
        extends AbstractFootballController<ProtocolService, ProtocolDTO, SaveProtocolDTO, Long>
        implements ProtocolController {

    public DefaultProtocolController(ProtocolService service) {
        super(service);
    }

}
