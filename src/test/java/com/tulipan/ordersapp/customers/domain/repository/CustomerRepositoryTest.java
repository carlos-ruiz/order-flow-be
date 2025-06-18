package com.tulipan.ordersapp.customers.domain.repository;

import com.tulipan.ordersapp.OrdersappApplication;
import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OrdersappApplication.class)
@Transactional
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findById_shouldReturnCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567892");
        customer = customerRepository.save(customer);

        Optional<Customer> customerFound = customerRepository.findById(customer.getId());
        assertTrue(customerFound.isPresent());
        assertEquals(customer.getId(), customerFound.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenCustomerDoesNotExist() {
        Long nonExistentId = 999L;
        Optional<Customer> customerFound = customerRepository.findById(nonExistentId);
        assertTrue(customerFound.isEmpty());
    }

    @Test
    void save_shouldPersistCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@mail.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Main St.");
        customer.setNote("This is a note.");

        customer = customerRepository.save(customer);

        assertNotNull(customer);
        assertNotNull(customer.getId());
    }

    @Test
    void update_shouldModifyCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setEmail("");
        customer.setPhone("1234567891");
        customer.setAddress("123 Main St.");
        customer.setNote("This is a note.");
        customer = customerRepository.save(customer);
        String name = customer.getName();

        customer.setName("Jane");
        customer = customerRepository.update(customer);

        assertNotEquals(name, customer.getName());
    }

    @Test
    void update_shouldThrowException_whenCustomerDoesNotExist() {
        Customer customer = new Customer();
        customer.setId(999L); // Non-existent ID
        customer.setName("John");
        customer.setPhone("1234567892");

        assertThrows(CustomerNotFoundException.class, () -> {
            customerRepository.update(customer);
        });
    }

    @Test
    void delete_shouldRemoveCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567893");
        customer = customerRepository.save(customer);

        customerRepository.delete(customer);
        Optional<Customer> customerFound = customerRepository.findById(customer.getId());
        assertTrue(customerFound.isEmpty());
    }

    @Test
    void deleteById_shouldRemoveCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567894");
        customer = customerRepository.save(customer);

        customerRepository.deleteById(customer.getId());
        Optional<Customer> customerFound = customerRepository.findById(customer.getId());
        assertTrue(customerFound.isEmpty());
    }

    @Test
    void deleteById_shouldDoNothing_whenCustomerDoesNotExist() {
        Long nonExistentId = 999L;
        int numberOfCustomers = customerRepository.findAll().size();
        customerRepository.deleteById(nonExistentId);
        int newNumberOfCustomers = customerRepository.findAll().size();
        assertEquals(numberOfCustomers, newNumberOfCustomers, "No customers should be deleted when ID does not exist");
        // No exception should be thrown, and no further assertions are needed
    }

    @Test
    void findAll_shouldReturnAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setPhone("1234567895");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setPhone("1234567896");
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findAll();
        assertTrue(customers.size() >= 2);
    }

}
