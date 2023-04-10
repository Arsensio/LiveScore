package com.example.livescore.controllers.group_info.impl;

import com.example.core.controller.AbstractFootballController;
import com.example.livescore.controllers.group_info.GroupInfoController;
import com.example.livescore.service.group_info.GroupInfoService;
import com.example.livescore.web.group_info.GroupInfoDTO;
import com.example.livescore.web.group_info.SaveGroupInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/group_info")
public class DefaultGroupInfoController
        extends AbstractFootballController<GroupInfoService, GroupInfoDTO,SaveGroupInfoDTO,Long>
        implements GroupInfoController {

    public DefaultGroupInfoController(GroupInfoService service) {
        super(service);
    }

    @Override
    @PostMapping("/create_draw_in_cup")
    public ResponseEntity<List<GroupInfoDTO>> createDrawInCup(@RequestBody List<SaveGroupInfoDTO> list) {
        return new ResponseEntity<>(service.createDrawInCup(list), OK);
    }
}
