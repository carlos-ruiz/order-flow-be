package com.tulipan.ordersapp.sellers.infrastructure.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tulipan.ordersapp.sellers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Customer;
import com.tulipan.ordersapp.sellers.domain.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public Customer createCustomer(@RequestBody Customer customer) {
    return customerService.save(customer);
  }

  @GetMapping("/{id}")
  public Customer getCustomerById(@PathVariable Long id) {
    return customerService.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
  }

  @GetMapping
  public Iterable<Customer> getAllCustomers() {
    return customerService.findAll();
  }

  @PutMapping("/{id}")
  public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
    customer.setId(id);
    return customerService.update(customer);
  }

  @DeleteMapping("/{id}")
  public void deleteCustomer(@PathVariable Long id) {
    Customer customer = customerService.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    customerService.delete(customer);
  }
}
