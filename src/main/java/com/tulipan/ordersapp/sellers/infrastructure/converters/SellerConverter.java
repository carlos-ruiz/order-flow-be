package com.tulipan.ordersapp.sellers.infrastructure.converters;

import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerConverter {

    public static Seller toModel(SellerEntity sellerEntity) {
        if (sellerEntity == null) {
            return null;
        }
        return new Seller(
            sellerEntity.getId(),
            sellerEntity.getName(),
            sellerEntity.getLastName(),
            sellerEntity.getAddress(),
            sellerEntity.getPhone(),
            sellerEntity.getEmail()
        );
    }

    public static SellerEntity toEntity(Seller seller) {
        if (seller == null) {
            return null;
        }
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setId(seller.getId());
        sellerEntity.setName(seller.getName());
        sellerEntity.setLastName(seller.getLastName());
        sellerEntity.setAddress(seller.getAddress());
        sellerEntity.setPhone(seller.getPhone());
        sellerEntity.setEmail(seller.getEmail());
        return sellerEntity;
    }
}
