package com.example.core.service;

import com.example.core.dto.AbstractEntity;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.exception.exceptions.UnsupportedMethodException;
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
        implements FootballService<E, RD, SD, I> {

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
                .toList();
    }

    @Override
    public RD findById(I id) {
        return findEntityById(id).toDTO();
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

    @Override
    public E findEntityById(I id) {
        return repository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.build(id, clazz.getName()));
    }

    @Override
    public RD save(SD dto) {
        throw UnsupportedMethodException.build("save", clazz.getName());
    }

    @Override
    public RD update(I id, SD dto) {
        throw UnsupportedMethodException.build("update", clazz.getName());
    }
}
