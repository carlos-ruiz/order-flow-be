package com.tulipan.ordersapp.products.domain.repository;

import com.tulipan.ordersapp.products.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Product product);

    void deleteById(Long id);

    void delete(Product product);

    List<Product> findAll();
}
