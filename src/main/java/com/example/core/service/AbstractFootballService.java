package com.example.core.service;

import com.example.core.dto.AbstractEntity;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
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
        this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<RD> findAll() {
        return repository.findAll()
                .stream()
                .map(E::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RD findById(I id) {
        Optional<E> referenceById = repository.findById(id);
        if (referenceById.isEmpty()) {
            throw ResourceNotFoundException.build(id, clazz.getName());
        } else {
            return referenceById.get().toDTO();
        }
    }

    @Override
    public RD delete(I id) {
        Optional<E> referenceById = repository.findById(id);
        if (referenceById.isEmpty()) {
            throw ResourceNotFoundException.build(id, clazz.getName());
        } else {
            repository.deleteById(id);
            return referenceById.get().toDTO();
        }
    }

    /***
     * left so that heirs do not have to implement unnecessary functionality
     */
    @Override
    public RD save(SD dto) {
        return null;
    }

    /***
     * left so that heirs do not have to implement unnecessary functionality
     */
    @Override
    public RD update(I id, SD dto) {
        return null;
    }
}
