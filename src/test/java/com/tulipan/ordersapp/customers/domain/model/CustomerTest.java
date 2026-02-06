package com.tulipan.ordersapp.customers.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {
    @Test
    void customerAllArgsConstructor_shouldInitializeFieldsCorrectly() {
        Customer customer = new Customer(1L, "John", "Doe", "john@mail.com", "1234567890", "123 Main St.", "This is a note.", true);

        assertEquals(1L, customer.getId());
        assertEquals("John", customer.getName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john@mail.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("123 Main St.", customer.getAddress());
        assertEquals("This is a note.", customer.getNote());
    }

    @Test
    void customerSetters_shouldUpdateFieldsCorrectly() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Jane");
        customer.setLastName("Smith");
        customer.setEmail("jane@mail.com");
        customer.setPhone("0987654321");
        customer.setAddress("456 Elm St.");
        customer.setNote("Updated note.");

        assertEquals(1L, customer.getId());
        assertEquals("Jane", customer.getName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("jane@mail.com", customer.getEmail());
        assertEquals("0987654321", customer.getPhone());
        assertEquals("456 Elm St.", customer.getAddress());
        assertEquals("Updated note.", customer.getNote());
    }

    @Test
    void customerEqualsAndHashCode_shouldBeConsistent() {
        Customer customer1 = new Customer(1L, "John", "Doe", "john@mail.com", "1234567890", "123 Main St.", "This is a note.", true);
        Customer customer2 = new Customer(1L, "John", "Doe", "john@mail.com", "1234567890", "123 Main St.", "This is a note.", true);

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void customerToString_shouldReturnCorrectFormat() {
        Customer customer = new Customer(1L, "John", "Doe", "john@mail.com", "1234567890", "123 Main St.", "This is a note.", true);
        String expected = "Customer(id=1, name=John, lastName=Doe, email=john@mail.com, phone=1234567890, address=123 Main St., note=This is a note.)";

        assertEquals(expected, customer.toString());
    }
}
