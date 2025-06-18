package com.tulipan.ordersapp.customers.application;

import com.tulipan.ordersapp.OrdersappApplication;
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
class CustomerServiceImplTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void findById_shouldReturnCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567892");
        customer = customerService.save(customer);

        Customer customerFound = customerService.findById(customer.getId()).orElse(null);
        assertNotNull(customerFound);
        assertEquals(customer.getId(), customerFound.getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenCustomerDoesNotExist() {
        Long nonExistentId = 999L;
        Optional<Customer> customerFound = customerService.findById(nonExistentId);
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

        customer = customerService.save(customer);

        assertNotNull(customer);
        assertNotNull(customer.getId());
    }

    @Test
    void delete_shouldRemoveCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567893");
        customer = customerService.save(customer);

        customerService.delete(customer);
        Customer customerFound = customerService.findById(customer.getId()).orElse(null);
        assertNull(customerFound);
    }

    @Test
    void deleteById_shouldRemoveCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhone("1234567894");
        customer = customerService.save(customer);

        customerService.deleteById(customer.getId());
        Customer customerFound = customerService.findById(customer.getId()).orElse(null);
        assertNull(customerFound);
    }

    @Test
    void deleteById_shouldDoNothing_whenCustomerDoesNotExist() {
        Long nonExistentId = 999L;
        int numberOfCustomers = customerService.findAll().size();
        customerService.deleteById(nonExistentId);
        int newNumberOfCustomers = customerService.findAll().size();
        assertEquals(numberOfCustomers, newNumberOfCustomers, "No customers should be deleted when ID does not exist");
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
        customer = customerService.save(customer);
        String name = customer.getName();

        customer.setName("Jane");
        customer = customerService.update(customer);

        assertNotEquals(name, customer.getName());
    }

    @Test
    void findAll_shouldReturnAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setPhone("1234567895");
        customerService.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setPhone("1234567896");
        customerService.save(customer2);

        List<Customer> customers = customerService.findAll();
        assertTrue(customers.size() >= 2);
    }
}
