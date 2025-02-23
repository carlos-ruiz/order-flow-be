package com.tulipan.ordersapp.customers.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.domain.service.CustomerService;
import com.tulipan.ordersapp.customers.infrastructure.repository.CustomerRepositoryAdapter;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepositoryAdapter repository;

  public CustomerServiceImpl(CustomerRepositoryAdapter repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Customer> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Customer save(Customer customer) {
    return repository.save(customer);
  }

  @Override
  public void delete(Customer customer) {
    repository.delete(customer);
  }

  @Override
  public Customer update(Customer customer) {
    return repository.update(customer);
  }

  @Override
  public List<Customer> findAll() {
    return repository.findAll();
  }

}
