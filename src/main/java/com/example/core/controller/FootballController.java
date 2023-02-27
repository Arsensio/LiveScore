package com.example.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * //todo: dopisat docu normalno
 * @param <D>
 * @param <S>
 */
public interface FootballController<D,S,P> {

    ResponseEntity<List<D>> getAll();

    ResponseEntity<D> findById(P id);

    ResponseEntity<D> save(@RequestBody S dto);

    ResponseEntity<D> put(@PathVariable P id, @RequestBody S dto);

    ResponseEntity<D> delete(@PathVariable P id);
}
