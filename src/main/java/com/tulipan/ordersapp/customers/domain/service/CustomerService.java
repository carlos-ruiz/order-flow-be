package com.tulipan.ordersapp.customers.domain.service;

import java.util.List;
import java.util.Optional;

import com.tulipan.ordersapp.customers.domain.model.Customer;

public interface CustomerService {
  Optional<Customer> findById(Long id);

  Customer save(Customer customer);

  void delete(Customer customer);

  Customer update(Customer customer);

  List<Customer> findAll();
}
