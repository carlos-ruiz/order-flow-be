package com.tulipan.ordersapp.sellers.domain.repository;

import com.tulipan.ordersapp.sellers.domain.model.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerRepository {
    Optional<Seller> findById(Long id);

    Seller save(Seller seller);

    Seller update(Seller seller);

    void deleteById(Long id);

    void delete(Seller seller);

    List<Seller> findAll();
}
