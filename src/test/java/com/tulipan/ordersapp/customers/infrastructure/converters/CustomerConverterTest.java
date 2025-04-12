package com.tulipan.ordersapp.customers.infrastructure.converters;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConverterTest {

    @Test
    void convertsCustomerEntityToModelSuccessfully() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setName("John");
        customerEntity.setLastName("Doe");
        customerEntity.setEmail("john.doe@example.com");
        customerEntity.setPhone("1234567890");
        customerEntity.setAddress("123 Main St");
        customerEntity.setNote("VIP");

        Customer customer = CustomerConverter.toModel(customerEntity);

        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals("VIP", customer.getNote());
    }

    @Test
    void returnsNullWhenCustomerEntityIsNull() {
        Customer customer = CustomerConverter.toModel(null);
        assertNull(customer);
    }

    @Test
    void convertsCustomerToEntitySuccessfully() {
        Customer customer = Customer.builder()
            .id(1L)
            .name("Jane")
            .lastName("Smith")
            .email("jane.smith@example.com")
            .phone("0987654321")
            .address("456 Elm St")
            .note("Regular")
            .build();

        CustomerEntity customerEntity = CustomerConverter.toEntity(customer);

        assertNotNull(customerEntity);
        assertEquals(1L, customerEntity.getId());
        assertEquals("Jane", customerEntity.getName());
        assertEquals("Smith", customerEntity.getLastName());
        assertEquals("jane.smith@example.com", customerEntity.getEmail());
        assertEquals("0987654321", customerEntity.getPhone());
        assertEquals("456 Elm St", customerEntity.getAddress());
        assertEquals("Regular", customerEntity.getNote());
    }

    @Test
    void returnsNullWhenCustomerIsNull() {
        CustomerEntity customerEntity = CustomerConverter.toEntity(null);
        assertNull(customerEntity);
    }

}
