package com.example.core.service;

import java.util.List;

/**
 * @param <D> - our DTO         //todo: dopisat docu normalno
 * @param <S> - Save DTO
 */
public interface FootballService<D, S, P> {

    List<D> getAll();

    D findById(P id);

    D save(S dto);

    D update(P id, S dto);

    D delete(P id);
}
