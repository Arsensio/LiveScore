package com.example.livescore.controllers;

import com.example.livescore.service.GroupServiceImpl;
import com.example.livescore.web.groups.GroupDTO;
import com.example.livescore.web.groups.SaveGroupDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {

    GroupServiceImpl groupService;

    @GetMapping
    public List<GroupDTO> findAll() {
        return groupService.getAll();
    }

    @PostMapping
    public GroupDTO save(@RequestBody SaveGroupDTO saveGroupDTO) {
        return groupService.postIndividual(saveGroupDTO);
    }

    @GetMapping("/{id}")
    public GroupDTO findById(@PathVariable Long id) {
        return groupService.getIndividual(id);
    }

    @PutMapping("/{id}")
    public GroupDTO update(@PathVariable Long id, @RequestBody SaveGroupDTO saveGroupDTO) {
        return groupService.putIndividual(id, saveGroupDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.deleteIndividual(id);
    }
}
