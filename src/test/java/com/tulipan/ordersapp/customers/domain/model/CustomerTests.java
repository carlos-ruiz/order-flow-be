package com.tulipan.ordersapp.customers.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTests {

    @Test
    void testCustomerModel() {
        Customer customer = new Customer();

        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@mail.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Main St.");
        customer.setNote("This is a note.");

        assertNotNull(customer);
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john@mail.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("123 Main St.", customer.getAddress());
        assertEquals("This is a note.", customer.getNote());
    }

    @Test
    void testCustomerNoArgsConstructor() {
        Customer customer = new Customer();

        assertNull(customer.getName());
        assertNull(customer.getLastName());
        assertNull(customer.getEmail());
        assertNull(customer.getPhone());
        assertNull(customer.getAddress());
        assertNull(customer.getNote());
    }
}
