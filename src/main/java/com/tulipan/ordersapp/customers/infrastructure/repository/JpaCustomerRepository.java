package com.tulipan.ordersapp.customers.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
