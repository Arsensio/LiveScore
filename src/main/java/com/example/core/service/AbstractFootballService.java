package com.example.core.service;

import com.example.core.dto.AbstractEntity;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.ParameterizedType;
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
    private Class<E> clazz;

    public AbstractFootballService(R repository) {
        this.repository = repository;
        clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<RD> findAll() {
        return repository.findAll().stream().map(E::toDTO).collect(Collectors.toList());
    }

    @Override
    public RD findById(I id) {
        try {
            E referenceById = repository.getReferenceById(id);
            return referenceById.toDTO();
        } catch (EntityNotFoundException exception) {
            throw ResourceNotFoundException.build(id, clazz.getName());
        }
    }

    @Override
    public RD delete(I id) {
        try {
            E referenceById = repository.getReferenceById(id);
            repository.deleteById(id);
            return referenceById.toDTO();
        } catch (EntityNotFoundException exception) {
            throw ResourceNotFoundException.build(id, clazz.getName());
        }
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
