package com.tulipan.ordersapp.customers.infrastructure.converters;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConverter {
    public static Customer toModel(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }
        return Customer.builder()
            .id(customerEntity.getId())
            .name(customerEntity.getName())
            .lastName(customerEntity.getLastName())
            .email(customerEntity.getEmail())
            .phone(customerEntity.getPhone())
            .address(customerEntity.getAddress())
            .note(customerEntity.getNote())
            .build();
    }

    public static CustomerEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setName(customer.getName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setAddress(customer.getAddress());
        customerEntity.setNote(customer.getNote());
        return customerEntity;
    }
}
