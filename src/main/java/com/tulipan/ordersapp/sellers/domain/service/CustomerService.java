package com.tulipan.ordersapp.sellers.domain.service;

import java.util.List;
import java.util.Optional;

import com.tulipan.ordersapp.sellers.domain.model.Customer;

public interface CustomerService {
  Optional<Customer> findById(Long id);

  Customer save(Customer customer);

  void delete(Customer customer);

  Customer update(Customer customer);

  List<Customer> findAll();
}
