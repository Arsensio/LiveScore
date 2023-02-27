package com.example.core.service;

import com.example.core.dto.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * //todo: dopisat docu normalno
 * @param <E>
 * @param <D>
 * @param <S>
 * @param <R>
 */
@Service
public abstract class AbstractFootballService<
        E extends AbstractEntity<D>,
        D, S, P,
        R extends JpaRepository<E, P>>
        implements FootballService<D, S,P> {

    protected final R repository;

    public AbstractFootballService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<D> getAll() {
        return repository.findAll().stream().map(E::toDTO).collect(Collectors.toList());
    }

    @Override
    public D findById(P id) {
        return repository.getReferenceById(id).toDTO();
    }

    @Override
    public D delete(P id) {
        E referenceById = repository.getReferenceById(id);
        repository.deleteById(id);
        return referenceById.toDTO();
    }

    @Override
    public D save(S dto) {
        return null;
    }

    @Override
    public D update(P id, S dto) {
        return null;
    }
}
