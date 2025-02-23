package com.tulipan.ordersapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.sellers.domain.exceptions.SellerNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SellerNotFoundException.class)
  public ResponseEntity<String> handleSellerNotFound(SellerNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(PlatformNotFoundException.class)
  public ResponseEntity<String> handlePlatformNotFound(PlatformNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

}
