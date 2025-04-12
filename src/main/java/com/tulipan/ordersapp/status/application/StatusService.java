package com.tulipan.ordersapp.status.application;

import com.tulipan.ordersapp.status.domain.model.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    Optional<Status> findById(Long id);

    Optional<Status> findByName(String name);

    Status save(Status status);

    Status update(Status status);

    void deleteById(Long id);

    void delete(Status status);

    List<Status> findAll();

    List<Status> findAllByIsActive(Boolean isActive);
}
