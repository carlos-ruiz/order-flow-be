package com.tulipan.ordersapp.sellers.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tulipan.ordersapp.sellers.domain.model.Platform;
import com.tulipan.ordersapp.sellers.domain.service.PlatformService;
import com.tulipan.ordersapp.sellers.infrastructure.repository.PlatformRepositoryAdapter;

@Service
public class PlatformServiceImpl implements PlatformService {

  private final PlatformRepositoryAdapter repository;

  public PlatformServiceImpl(PlatformRepositoryAdapter repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Platform> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Platform save(Platform platform) {
    return repository.save(platform);
  }

  @Override
  public void delete(Platform platform) {
    repository.delete(platform);
  }

  @Override
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public Platform update(Platform platform) {
    return repository.update(platform);
  }

  @Override
  public List<Platform> findAll() {
    return repository.findAll();
  }

}
