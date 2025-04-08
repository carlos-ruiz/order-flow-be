package com.tulipan.ordersapp.products.infrastructure.repository;

import com.tulipan.ordersapp.products.infrastructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
}
