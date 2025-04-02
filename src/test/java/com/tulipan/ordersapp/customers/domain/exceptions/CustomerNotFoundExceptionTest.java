package com.tulipan.ordersapp.customers.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CustomerNotFoundExceptionTest {

    @Test
    void customerNotFoundException_shouldContainCorrectMessage() {
        Long nonExistentId = 999L;
        CustomerNotFoundException exception = new CustomerNotFoundException(nonExistentId);
        assertEquals("Customer with id " + nonExistentId + " not found.", exception.getMessage());
    }

    @Test
    void customerNotFoundException_shouldBeInstanceOfRuntimeException() {
        CustomerNotFoundException exception = new CustomerNotFoundException(1L);
        assertInstanceOf(RuntimeException.class, exception);
    }
}
