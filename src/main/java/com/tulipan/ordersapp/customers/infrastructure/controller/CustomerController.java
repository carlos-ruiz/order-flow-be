package com.tulipan.ordersapp.customers.infrastructure.controller;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.domain.service.CustomerService;
import com.tulipan.ordersapp.customers.infrastructure.dto.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(savedCustomer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
        return new ResponseEntity<>(toDTO(customer), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = toModel(customerDTO);
        customer.setId(id);
        Customer updatedCustomer = customerService.update(customer);
        return ResponseEntity.ok(toDTO(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
        customerService.delete(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Customer toModel(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setLastName(dto.getLastName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setNote(dto.getNote());
        return customer;
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setLastName(customer.getLastName());
        dto.setAddress(customer.getAddress());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setNote(customer.getNote());
        return dto;
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
