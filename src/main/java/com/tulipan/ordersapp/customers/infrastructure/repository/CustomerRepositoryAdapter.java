package com.tulipan.ordersapp.customers.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.tulipan.ordersapp.customers.domain.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;

@Component
public class CustomerRepositoryAdapter implements CustomerRepository {
  private final JpaCustomerRepository jpaCustomerRepository;

  public CustomerRepositoryAdapter(JpaCustomerRepository repository) {
    this.jpaCustomerRepository = repository;
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
    CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
    return toModel(savedEntity);
  }

  public Optional<Customer> findById(Long id) {
    return jpaCustomerRepository.findById(id)
        .map(this::toModel);
  }

  public List<Customer> findAll() {
    return jpaCustomerRepository.findAll()
        .stream()
        .map(this::toModel)
        .toList();
  }

  public void deleteById(Long id) {
    jpaCustomerRepository.deleteById(id);
  }

  public void delete(Customer customer) {
    CustomerEntity entity = toEntity(customer);
    jpaCustomerRepository.delete(entity);
  }

  public Customer update(Customer customer) {
    CustomerEntity entity = jpaCustomerRepository.findById(customer.getId())
        .orElseThrow(() -> new CustomerNotFoundException(customer.getId()));
    entity.setName(customer.getName());
    entity.setLastName(customer.getLastName());
    entity.setAddress(customer.getAddress());
    entity.setPhone(customer.getPhone());
    entity.setEmail(customer.getEmail());
    entity.setNote(customer.getNote());
    CustomerEntity updatedEntity = jpaCustomerRepository.save(entity);
    return toModel(updatedEntity);
  }

}
