package com.tulipan.ordersapp.platforms.infrastructure.converters;

import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlatformConverter {

    public static Platform toModel(PlatformEntity platformEntity) {
        if (platformEntity == null) {
            return null;
        }
        Platform platform = new Platform();
        platform.setId(platformEntity.getId());
        platform.setName(platformEntity.getName());
        platform.setCustomerFee(platformEntity.getCustomerFee());
        platform.setSellerCommission(platformEntity.getSellerCommission());
        platform.setActive(platformEntity.getActive());
        return platform;
    }

    public static PlatformEntity toEntity(Platform platform) {
        if (platform == null) {
            return null;
        }
        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setId(platform.getId());
        platformEntity.setName(platform.getName());
        platformEntity.setCustomerFee(platform.getCustomerFee());
        platformEntity.setSellerCommission(platform.getSellerCommission());
        platformEntity.setActive(platform.getActive());
        return platformEntity;
    }
}
