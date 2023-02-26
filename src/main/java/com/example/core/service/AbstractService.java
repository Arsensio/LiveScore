package com.example.core.service;

import com.example.core.dto.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService<E extends AbstractEntity<D>, D, S, R extends JpaRepository<E, Long>> implements Service<D, S> {

    R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<D> getAll() {
        return repository.findAll().stream().map(E::toDTO).collect(Collectors.toList());
    }

    @Override
    public D findById(long id) {
        return repository.getReferenceById(id).toDTO();
    }

    @Override
    public D delete(long id) {
        E referenceById = repository.getReferenceById(id);
        repository.deleteById(id);
        return referenceById.toDTO();
    }
}
