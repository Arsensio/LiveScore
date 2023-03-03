package com.example.core.controller;

import com.example.core.service.FootballService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * todo: complete documentation
 *
 * @param <S>
 * @param <RD>
 * @param <SD>
 * @param <I>
 */
@Controller
public abstract class AbstractFootballController<
        S extends FootballService<RD, SD, I>,
        RD, SD, I>
        implements FootballController<RD, SD, I> {

    protected final S service;

    public AbstractFootballController(S service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RD>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RD> findById(@PathVariable I id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping()
    public ResponseEntity<RD> save(SD dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RD> put(@PathVariable I id, SD dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<RD> delete(@PathVariable I id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
