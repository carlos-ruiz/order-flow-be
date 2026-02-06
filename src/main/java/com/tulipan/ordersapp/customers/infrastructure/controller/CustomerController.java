package com.tulipan.ordersapp.customers.infrastructure.controller;

import com.tulipan.ordersapp.customers.application.CustomerService;
import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.infrastructure.dto.CustomerRequestDTO;
import com.tulipan.ordersapp.customers.infrastructure.dto.CustomerResponseDTO;
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

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequest) {
        Customer customer = toModel(customerRequest);

        Customer savedCustomer = customerService.save(customer);
        CustomerResponseDTO responseDTO = toResponseDTO(savedCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
        CustomerResponseDTO customerResponseDTO = toResponseDTO(customer);
        return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        List<CustomerResponseDTO> customerResponseDTOs = customers.stream()
            .map(this::toResponseDTO)
            .toList();
        return ResponseEntity.ok(customerResponseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer customer = toModel(customerRequestDTO);
        customer.setId(id);
        Customer updatedCustomer = customerService.update(customer);
        CustomerResponseDTO customerResponseDTO = toResponseDTO(updatedCustomer);
        return ResponseEntity.ok(customerResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerService.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private CustomerResponseDTO toResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
            .id(customer.getId())
            .name(customer.getName())
            .lastName(customer.getLastName())
            .email(customer.getEmail())
            .phone(customer.getPhone())
            .address(customer.getAddress())
            .note(customer.getNote())
            .active(customer.getActive())
            .build();
    }

    private Customer toModel(CustomerRequestDTO customerRequestDTO) {
        return Customer.builder()
            .name(customerRequestDTO.getName())
            .lastName(customerRequestDTO.getLastName())
            .email(customerRequestDTO.getEmail())
            .phone(customerRequestDTO.getPhone())
            .address(customerRequestDTO.getAddress())
            .note(customerRequestDTO.getNote())
            .active(customerRequestDTO.getActive())
            .build();
    }
}
