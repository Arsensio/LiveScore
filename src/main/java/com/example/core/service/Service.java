package com.example.core.service;

import java.util.List;

/**
 * @param <D> - our DTO
 * @param <S> - Save DTO
 */
public interface Service<D, S> {

    List<D> getAll();

    D findById(long id);

    D save(S dto);

    D update(long id, S dto);

    D delete(long id);
}
