package com.example.livescore.service;

import java.util.List;

public interface MainService<T, V> {

    List<V> getAll();

    V getIndividual(Long id);

    V postIndividual(T t);

    V putIndividual(Long id, T t);

    void deleteIndividual(Long id);
}
