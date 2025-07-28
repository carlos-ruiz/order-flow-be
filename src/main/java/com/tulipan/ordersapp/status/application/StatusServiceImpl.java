package com.tulipan.ordersapp.status.application;

import com.tulipan.ordersapp.status.domain.exceptions.StatusNotFoundException;
import com.tulipan.ordersapp.status.domain.model.Status;
import com.tulipan.ordersapp.status.domain.repository.StatusRepository;
import com.tulipan.ordersapp.status.infrastructure.converters.StatusConverter;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusRepository.findById(id);
    }

    @Override
    public Optional<Status> findByName(String name) {
        return statusRepository.findByName(name);
    }

    @Override
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status update(Status status) {
        Status existingStatus = statusRepository.findById(status.getId())
            .orElseThrow(() -> new StatusNotFoundException(status.getId()));
        StatusEntity statusEntity = StatusConverter.toEntity(existingStatus);
        statusEntity.setName(status.getName());
        statusEntity.setIsActive(status.getIsActive());
        return statusRepository.save(StatusConverter.toModel(statusEntity));
    }

    @Override
    public void deleteById(Long id) {
        statusRepository.deleteById(id);
    }

    @Override
    public void delete(Status status) {
        statusRepository.delete(status);
    }

    @Override
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public List<Status> findAllByIsActive(Boolean isActive) {
        return statusRepository.findAllByIsActive(isActive);
    }

    @Override
    public boolean existsById(Long id) {
        return statusRepository.existsById(id);
    }
}
