package com.example.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * todo: complete documentation
 *
 * @param <RD>
 * @param <SD>
 * @param <I>
 */
public interface FootballController<RD, SD, I> {

    ResponseEntity<List<RD>> findAll();

    ResponseEntity<RD> findById(I id);

    ResponseEntity<RD> save(@RequestBody SD dto);

    ResponseEntity<RD> put(@PathVariable I id, @RequestBody SD dto);

    ResponseEntity<RD> delete(@PathVariable I id);
}
