package com.tulipan.ordersapp.status.infrastructure.repository;

import com.tulipan.ordersapp.status.domain.model.Status;
import com.tulipan.ordersapp.status.domain.repository.StatusRepository;
import com.tulipan.ordersapp.status.infrastructure.converters.StatusConverter;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StatusRepositoryAdapter implements StatusRepository {
    private final JpaStatusRepository jpaStatusRepository;

    public StatusRepositoryAdapter(JpaStatusRepository jpaStatusRepository) {
        this.jpaStatusRepository = jpaStatusRepository;
    }

    @Override
    public Optional<Status> findById(Long id) {
        return jpaStatusRepository.findById(id)
            .map(StatusConverter::toModel);
    }

    @Override
    public Optional<Status> findByName(String name) {
        return jpaStatusRepository.findByName(name)
            .map(StatusConverter::toModel);
    }

    @Override
    public Status save(Status status) {
        StatusEntity entity = StatusConverter.toEntity(status);
        StatusEntity savedEntity = jpaStatusRepository.save(entity);
        return StatusConverter.toModel(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaStatusRepository.deleteById(id);
    }

    @Override
    public void delete(Status status) {
        StatusEntity entity = StatusConverter.toEntity(status);
        jpaStatusRepository.delete(entity);
    }

    @Override
    public List<Status> findAll() {
        return jpaStatusRepository.findAll().stream().map(StatusConverter::toModel).toList();
    }

    @Override
    public List<Status> findAllByActive(Boolean active) {
        return jpaStatusRepository.findAllByActive(active)
            .stream()
            .map(StatusConverter::toModel)
            .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return jpaStatusRepository.existsById(id);
    }
}
