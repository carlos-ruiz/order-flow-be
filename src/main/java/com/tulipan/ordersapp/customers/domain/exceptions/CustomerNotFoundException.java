package com.tulipan.ordersapp.customers.domain.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer with id " + id + " not found.");
    }

    public CustomerNotFoundException(String name) {
        super("Customer with name " + name + " not found.");
    }
}
