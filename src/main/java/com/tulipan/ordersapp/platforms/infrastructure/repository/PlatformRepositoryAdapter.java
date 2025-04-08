package com.tulipan.ordersapp.platforms.infrastructure.repository;

import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.domain.repository.PlatformRepository;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlatformRepositoryAdapter implements PlatformRepository {
    private final JpaPlatformRepository jpaPlatformRepository;

    public PlatformRepositoryAdapter(JpaPlatformRepository jpaPlatformRepository) {
        this.jpaPlatformRepository = jpaPlatformRepository;
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
        PlatformEntity savedEntity = jpaPlatformRepository.save(entity);
        return toModel(savedEntity);
    }

    public Optional<Platform> findById(Long id) {
        return jpaPlatformRepository.findById(id).map(this::toModel);
    }

    public List<Platform> findAll() {
        return jpaPlatformRepository.findAll().stream().map(this::toModel).toList();
    }

    public void delete(Platform platform) {
        jpaPlatformRepository.delete(toEntity(platform));
    }

    public void deleteById(Long id) {
        jpaPlatformRepository.deleteById(id);
    }

    public Platform update(Platform platform) {
        PlatformEntity entity = jpaPlatformRepository.findById(platform.getId())
            .orElseThrow(() -> new PlatformNotFoundException(platform.getName()));
        entity.setName(platform.getName());
        entity.setCustomerFee(platform.getCustomerFee());
        entity.setSellerCommission(platform.getSellerCommission());
        if (platform.getActive() != null) {
            entity.setActive(platform.getActive());
        } else {
            entity.setActive(true);
        }
        PlatformEntity updatedEntity = jpaPlatformRepository.save(entity);
        return toModel(updatedEntity);
    }

}
