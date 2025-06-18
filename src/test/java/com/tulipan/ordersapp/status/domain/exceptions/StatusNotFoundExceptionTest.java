package com.tulipan.ordersapp.status.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusNotFoundExceptionTest {

    @Test
    void testStatusNotFoundExceptionWithId() {
        Long id = 1L;
        StatusNotFoundException exception = new StatusNotFoundException(id);
        assertEquals("Status with id " + id + " not found", exception.getMessage());
    }

    @Test
    void testStatusNotFoundExceptionWithName() {
        String name = "Pending";
        StatusNotFoundException exception = new StatusNotFoundException(name);
        assertEquals("Status with name " + name + " not found", exception.getMessage());
    }

}
