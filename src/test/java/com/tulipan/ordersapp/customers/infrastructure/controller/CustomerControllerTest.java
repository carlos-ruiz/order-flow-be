package com.tulipan.ordersapp.customers.infrastructure.controller;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.domain.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void createCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"lastName\":\"Doe\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.findById(anyLong())).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerById_NotFound() throws Exception {
        when(customerService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.update(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"lastName\":\"Doe\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCustomer_Success() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.findById(anyLong())).thenReturn(Optional.of(customer));

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomer_NotFound() throws Exception {
        when(customerService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNotFound());
    }
}
