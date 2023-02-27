package com.example.core.controller;

import com.example.core.service.FootballService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * //todo: dopisat docu normalno
 *
 * @param <S>  - Service
 * @param <D>  - DTO
 * @param <D2> - SaveDTO
 */

@Controller
public abstract class AbstractFootballController<
        S extends FootballService<D, D2>,
        D, D2>
        implements FootballController<D, D2> {

    protected final S service;

    public AbstractFootballController(S service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<D>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping()
    public ResponseEntity<D> save(D2 dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<D> put(@PathVariable long id, D2 dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<D> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
