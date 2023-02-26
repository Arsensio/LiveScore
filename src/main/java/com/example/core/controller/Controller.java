package com.example.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface Controller<D,S> {

    ResponseEntity<List<D>> getAll();

    ResponseEntity<D> findById(long id);

    ResponseEntity<D> save(@RequestBody S dto);

    ResponseEntity<D> put(@PathVariable long id, @RequestBody S dto);

    ResponseEntity<D> delete(@PathVariable long id);
}
