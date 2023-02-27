package com.example.core.service;

import java.util.List;

/**
 * @param <D> - our DTO         //todo: dopisat docu normalno
 * @param <S> - Save DTO
 */
public interface FootballService<D, S> {

    List<D> getAll();

    D findById(long id);

    D save(S dto);

    D update(long id, S dto);

    D delete(long id);
}
