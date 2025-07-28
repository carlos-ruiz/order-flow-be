package com.tulipan.ordersapp.status.domain.repository;

import com.tulipan.ordersapp.status.domain.model.Status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository {
    Optional<Status> findById(Long id);

    Optional<Status> findByName(String name);

    Status save(Status status);

    void deleteById(Long id);

    void delete(Status status);

    List<Status> findAll();

    List<Status> findAllByIsActive(Boolean isActive);

    boolean existsById(Long id);
}
