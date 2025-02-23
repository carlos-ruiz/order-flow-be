package com.tulipan.ordersapp.customers.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;

@Component
public class CustomerRepositoryAdapter {
  private final CustomerRepository customerRepository;

  public CustomerRepositoryAdapter(CustomerRepository repository) {
    this.customerRepository = repository;
  }

  private CustomerEntity toEntity(Customer customer) {
    CustomerEntity entity = new CustomerEntity();
    entity.setId(customer.getId());
    entity.setName(customer.getName());
    entity.setLastName(customer.getLastName());
    entity.setAddress(customer.getAddress());
    entity.setPhone(customer.getPhone());
    entity.setEmail(customer.getEmail());
    entity.setNote(customer.getNote());
    return entity;
  }

  private Customer toModel(CustomerEntity entity) {
    Customer customer = new Customer();
    customer.setId(entity.getId());
    customer.setName(entity.getName());
    customer.setLastName(entity.getLastName());
    customer.setAddress(entity.getAddress());
    customer.setPhone(entity.getPhone());
    customer.setEmail(entity.getEmail());
    customer.setNote(entity.getNote());
    return customer;
  }

  public Customer save(Customer customer) {
    CustomerEntity entity = toEntity(customer);
    CustomerEntity savedEntity = customerRepository.save(entity);
    return toModel(savedEntity);
  }

  public Optional<Customer> findById(Long id) {
    return customerRepository.findById(id)
        .map(this::toModel);
  }

  public List<Customer> findAll() {
    return customerRepository.findAll()
        .stream()
        .map(this::toModel)
        .toList();
  }

  public void deleteById(Long id) {
    customerRepository.deleteById(id);
  }

  public void delete(Customer customer) {
    CustomerEntity entity = toEntity(customer);
    customerRepository.delete(entity);
  }

  public Customer update(Customer customer) {
    CustomerEntity entity = customerRepository.findById(customer.getId())
        .orElseThrow(() -> new CustomerNotFoundException(customer.getId()));
    entity.setName(customer.getName());
    entity.setLastName(customer.getLastName());
    entity.setAddress(customer.getAddress());
    entity.setPhone(customer.getPhone());
    entity.setEmail(customer.getEmail());
    entity.setNote(customer.getNote());
    CustomerEntity updatedEntity = customerRepository.save(entity);
    return toModel(updatedEntity);
  }

}
