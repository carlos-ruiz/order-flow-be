package com.tulipan.ordersapp.sellers.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.domain.service.SellerService;
import com.tulipan.ordersapp.sellers.infrastructure.repository.SellerRepositoryAdapter;

@Service
public class SellerServiceImpl implements SellerService {
  private final SellerRepositoryAdapter repository;

  public SellerServiceImpl(SellerRepositoryAdapter repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Seller> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Seller save(Seller seller) {
    return repository.save(seller);
  }

  @Override
  public void delete(Seller seller) {
    repository.delete(seller);
  }

  @Override
  public Seller update(Seller seller) {
    return repository.update(seller);
  }

  @Override
  public List<Seller> findAll() {
    return repository.findAll();
  }
}
