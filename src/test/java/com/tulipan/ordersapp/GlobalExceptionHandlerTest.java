package com.tulipan.ordersapp;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.sellers.domain.exceptions.SellerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleSellerNotFound_ReturnsNotFoundResponse() {
        SellerNotFoundException exception = new SellerNotFoundException("Carlos");

        ResponseEntity<String> response = globalExceptionHandler.handleSellerNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller with name Carlos not found.", response.getBody());
    }

    @Test
    void handleSellerByIdNotFound_ReturnsNotFoundResponse() {
        SellerNotFoundException exception = new SellerNotFoundException(1L);

        ResponseEntity<String> response = globalExceptionHandler.handleSellerNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Seller with id 1 not found.", response.getBody());
    }

    @Test
    void handleCustomerNotFound_ReturnsNotFoundResponse() {
        CustomerNotFoundException exception = new CustomerNotFoundException("Carlos");

        ResponseEntity<String> response = globalExceptionHandler.handleCustomerNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer with name Carlos not found.", response.getBody());
    }

    @Test
    void handleCustomerByIdNotFound_ReturnsNotFoundResponse() {
        CustomerNotFoundException exception = new CustomerNotFoundException(1L);

        ResponseEntity<String> response = globalExceptionHandler.handleCustomerNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer with id 1 not found.", response.getBody());
    }

    @Test
    void handlePlatformNotFound_ReturnsNotFoundResponse() {
        PlatformNotFoundException exception = new PlatformNotFoundException("Amazon");

        ResponseEntity<String> response = globalExceptionHandler.handlePlatformNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Platform with name Amazon not found.", response.getBody());
    }

    @Test
    void handlePlatformByIdNotFound_ReturnsNotFoundResponse() {
        PlatformNotFoundException exception = new PlatformNotFoundException(1L);

        ResponseEntity<String> response = globalExceptionHandler.handlePlatformNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Platform with id 1 not found.", response.getBody());
    }
}
