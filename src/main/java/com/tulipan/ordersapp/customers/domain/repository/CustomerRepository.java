package com.tulipan.ordersapp.customers.domain.repository;

import com.tulipan.ordersapp.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    Customer update(Customer customer);
    void delete(Customer customer);
    void deleteById(Long id);
    List<Customer> findAll();
}
