package com.tulipan.ordersapp.customers.application;

import com.tulipan.ordersapp.customers.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    void delete(Customer customer);

    void deleteById(Long id);

    Customer update(Customer customer);

    List<Customer> findAll();
}
