package com.tulipan.ordersapp.platforms.domain.service;

import java.util.List;
import java.util.Optional;

import com.tulipan.ordersapp.platforms.domain.model.Platform;

public interface PlatformService {
  Optional<Platform> findById(Long id);

  Platform save(Platform platform);

  void delete(Platform platform);

  void deleteById(Long id);

  Platform update(Platform platform);

  List<Platform> findAll();

}
