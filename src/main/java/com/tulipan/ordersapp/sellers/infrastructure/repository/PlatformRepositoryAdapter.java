package com.tulipan.ordersapp.sellers.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tulipan.ordersapp.sellers.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Platform;
import com.tulipan.ordersapp.sellers.infrastructure.entities.PlatformEntity;

@Component
public class PlatformRepositoryAdapter {
  private final PlatformRepository platformRepository;

  public PlatformRepositoryAdapter(PlatformRepository platformRepository) {
    this.platformRepository = platformRepository;
  }

  private PlatformEntity toEntity(Platform platform) {
    PlatformEntity entity = new PlatformEntity();
    entity.setId(platform.getId());
    entity.setName(platform.getName());
    entity.setCustomerFee(platform.getCustomerFee());
    entity.setSellerCommission(platform.getSellerCommission());
    entity.setActive(platform.getActive());
    return entity;
  }

  private Platform toModel(PlatformEntity entity) {
    Platform platform = new Platform();
    platform.setId(entity.getId());
    platform.setName(entity.getName());
    platform.setCustomerFee(entity.getCustomerFee());
    platform.setSellerCommission(entity.getSellerCommission());
    platform.setActive(entity.getActive());
    return platform;
  }

  public Platform save(Platform platform) {
    PlatformEntity entity = toEntity(platform);
    PlatformEntity savedEntity = platformRepository.save(entity);
    return toModel(savedEntity);
  }

  public Optional<Platform> findById(Long id) {
    return platformRepository.findById(id).map(this::toModel);
  }

  public List<Platform> findAll() {
    return platformRepository.findAll().stream().map(this::toModel).toList();
  }

  public void delete(Platform platform) {
    platformRepository.delete(toEntity(platform));
  }

  public void deleteById(Long id) {
    platformRepository.deleteById(id);
  }

  public Platform update(Platform platform) {
    PlatformEntity entity = platformRepository.findById(platform.getId())
        .orElseThrow(() -> new PlatformNotFoundException(platform.getName()));
    entity.setName(platform.getName());
    entity.setCustomerFee(platform.getCustomerFee());
    entity.setSellerCommission(platform.getSellerCommission());
    if (platform.getActive() != null) {
      entity.setActive(platform.getActive());
    }
    PlatformEntity updatedEntity = platformRepository.save(entity);
    return toModel(updatedEntity);
  }

}
