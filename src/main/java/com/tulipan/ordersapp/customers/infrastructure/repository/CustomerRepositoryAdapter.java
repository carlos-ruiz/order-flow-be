package com.tulipan.ordersapp.customers.infrastructure.repository;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.domain.repository.CustomerRepository;
import com.tulipan.ordersapp.customers.infrastructure.converters.CustomerConverter;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.tulipan.ordersapp.customers.infrastructure.converters.CustomerConverter.toEntity;
import static com.tulipan.ordersapp.customers.infrastructure.converters.CustomerConverter.toModel;

@Slf4j
@Component
public class CustomerRepositoryAdapter implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;

    public CustomerRepositoryAdapter(JpaCustomerRepository repository) {
        this.jpaCustomerRepository = repository;
    }

    public Customer save(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
        return toModel(savedEntity);
    }

    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id)
            .map(CustomerConverter::toModel);
    }

    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll()
            .stream()
            .map(CustomerConverter::toModel)
            .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return jpaCustomerRepository.existsById(id);
    }

    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }

    public void delete(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        jpaCustomerRepository.delete(entity);
    }

    public Customer update(Customer customer) {
        log.info("Updating customer {}", customer);
        CustomerEntity entity = jpaCustomerRepository.findById(customer.getId())
            .orElseThrow(() -> new CustomerNotFoundException(customer.getId()));
        customer.setId(entity.getId());
        entity = toEntity(customer);
        CustomerEntity updatedEntity = jpaCustomerRepository.save(entity);
        return toModel(updatedEntity);
    }

}
