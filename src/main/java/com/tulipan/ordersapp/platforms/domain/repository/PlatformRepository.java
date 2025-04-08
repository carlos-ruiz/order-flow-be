package com.tulipan.ordersapp.platforms.domain.repository;

import com.tulipan.ordersapp.platforms.domain.model.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository {
    Optional<Platform> findById(Long id);

    Platform save(Platform platform);

    void delete(Platform platform);

    void deleteById(Long id);

    Platform update(Platform platform);

    List<Platform> findAll();
}
