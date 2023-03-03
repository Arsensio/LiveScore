package com.example.core.service;

import com.example.core.dto.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * todo: complete documentation
 *
 * @param <E>
 * @param <RD>
 * @param <SD>
 * @param <I>
 * @param <R>
 */
@Service
public abstract class AbstractFootballService<
        E extends AbstractEntity<RD>,
        RD, SD, I,
        R extends JpaRepository<E, I>>
        implements FootballService<RD, SD, I> {

    protected final R repository;

    public AbstractFootballService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<RD> findAll() {
        return repository.findAll().stream().map(E::toDTO).collect(Collectors.toList());
    }

    @Override
    public RD findById(I id) {
        return repository.getReferenceById(id).toDTO();
    }

    @Override
    public RD delete(I id) {
        E referenceById = repository.getReferenceById(id);
        repository.deleteById(id);
        return referenceById.toDTO();
    }

    // todo
    @Override
    public RD save(SD dto) {
        return null;
    }

    // todo
    @Override
    public RD update(I id, SD dto) {
        return null;
    }
}
