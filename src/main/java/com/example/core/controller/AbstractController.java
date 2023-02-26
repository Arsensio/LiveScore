package com.example.core.controller;

import com.example.core.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @param <S>  - Service
 * @param <D>  - DTO
 * @param <D2> - SaveDTO
 */

public abstract class AbstractController<S extends Service<D, D2>, D, D2> implements Controller<D, D2> {

    protected final S service;

    public AbstractController(S service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<D>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<D> findById(long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<D> save(D2 dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<D> put(long id, D2 dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<D> delete(long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
