package com.tulipan.ordersapp.sellers.domain;

public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(Long id) {
    super("Customer with id " + id + " not found.");
  }
}
