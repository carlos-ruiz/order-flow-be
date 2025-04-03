package com.tulipan.ordersapp.platforms.infrastructure.repository;

import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlatformRepositoryAdapter {
    private final PlatformRepository platformRepository;

    public PlatformRepositoryAdapter(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    private PlatformEntity toEntity(Platform platform) {
        return PlatformEntity.builder()
            .id(platform.getId())
            .name(platform.getName())
            .customerFee(platform.getCustomerFee())
            .sellerCommission(platform.getSellerCommission())
            .active(platform.getActive())
            .build();
    }

    private Platform toModel(PlatformEntity entity) {
        return Platform.builder()
            .id(entity.getId())
            .name(entity.getName())
            .customerFee(entity.getCustomerFee())
            .sellerCommission(entity.getSellerCommission())
            .active(entity.getActive())
            .build();
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
