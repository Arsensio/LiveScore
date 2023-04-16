package com.example.livescore.controllers.event_info.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.event_info.EventInfoController;
import com.example.livescore.service.even_info.EventInfoService;
import com.example.livescore.web.assists.AssistDTO;
import com.example.livescore.web.assists.SaveAssistsDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goal_info")
public class DefaultEventInfoController
        extends AbstractFootballController<EventInfoService, AssistDTO, SaveAssistsDTO, Long>
        implements EventInfoController {

    public DefaultEventInfoController(EventInfoService service) {
        super(service);
    }
}
