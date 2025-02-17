package com.tulipan.ordersapp.sellers.domain.service;

import java.util.List;
import java.util.Optional;

import com.tulipan.ordersapp.sellers.domain.model.Seller;

public interface SellerService {
  Optional<Seller> findById(Long id);

  Seller save(Seller seller);

  void delete(Seller seller);

  Seller update(Seller seller);

  List<Seller> findAll();
}
