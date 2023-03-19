package com.example.livescore.controllers.assists.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.assists.AssistController;
import com.example.livescore.service.assists.AssistService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assists")
public class DefaultAssistController
        extends AbstractFootballController<AssistService, AssistDTO, SaveAssistsDTO, Long>
        implements AssistController {

    public DefaultAssistController(AssistService service) {
        super(service);
    }
}
