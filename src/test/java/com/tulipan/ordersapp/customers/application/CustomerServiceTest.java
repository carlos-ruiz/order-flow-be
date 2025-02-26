package com.tulipan.ordersapp.customers.application;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.domain.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@mail.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Main St.");
        customer.setNote("This is a note.");

        customer = customerService.save(customer);

        assertNotNull(customer);
        assertNotNull(customer.getId());
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("");
        customer.setPhone("1234567891");
        customer.setAddress("123 Main St.");
        customer.setNote("This is a note.");
        customer = customerService.save(customer);
        String name = customer.getName();

        customer.setName("Jane");
        customer = customerService.update(customer);

        assertNotEquals(name, customer.getName());
    }

    @Test
    void findCustomerById() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567892");
        customer = customerService.save(customer);

        Customer customerFound = customerService.findById(customer.getId()).orElse(null);
        assertNotNull(customerFound);
    }

    @Test
    void deleteCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567893");
        customer = customerService.save(customer);

        customerService.delete(customer);
        Customer customerFound = customerService.findById(customer.getId()).orElse(null);
        assertNull(customerFound);
    }
}
