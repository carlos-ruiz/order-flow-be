package com.tulipan.ordersapp.orders.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tulipan.ordersapp.orders.application.OrderService;
import com.tulipan.ordersapp.orders.domain.exceptions.OrderNotFoundException;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.dto.OrderResponseDTO;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
        Platform platform = Platform.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.valueOf(15.00))
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        order = Order.builder()
            .id(1L)
            .dateTime(LocalDateTime.now())
            .discount(BigDecimal.ZERO)
            .platform(platform)
            .build();
    }

    @Test
    void createOrder() throws Exception {
        when(orderService.save(any(LocalDateTime.class), any(BigDecimal.class), any(Long.class)))
            .thenReturn(order);

        String response = mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content("{\"dateTime\":\"2023-10-01T10:00:00\",\"discount\":0,\"platformId\":1}"))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderResponseDTO orderResponse = objectMapper.readValue(response, OrderResponseDTO.class);

        assertEquals(order.getId(), orderResponse.getId());
        assertEquals(order.getDateTime(), orderResponse.getDateTime());
        assertEquals(order.getDiscount(), orderResponse.getDiscount());
        assertEquals(order.getPlatform().getId(), orderResponse.getPlatformId());
    }

    @Test
    void createOrderWithNullPlatformId() throws Exception {
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content("{\"dateTime\":\"2023-10-01T10:00:00\",\"discount\":0,\"platformId\":null}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void findAllOrders() throws Exception {
        when(orderService.findAll()).thenReturn(List.of(order));

        String response = mockMvc.perform(get("/orders/all"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<OrderResponseDTO> orderResponses = objectMapper.readValue(response, new TypeReference<List<OrderResponseDTO>>() {
        });

        assertEquals(1, orderResponses.size());
        assertEquals(order.getId(), orderResponses.getFirst().getId());
    }

    @Test
    void findById() throws Exception {
        when(orderService.findById(1L)).thenReturn(Optional.of(order));

        String response = mockMvc.perform(get("/orders/1"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderResponseDTO orderResponse = objectMapper.readValue(response, OrderResponseDTO.class);

        assertEquals(order.getId(), orderResponse.getId());
        assertEquals(order.getDateTime(), orderResponse.getDateTime());
        assertEquals(order.getDiscount(), orderResponse.getDiscount());
        assertEquals(order.getPlatform().getId(), orderResponse.getPlatformId());
    }

    @Test
    void findByIdNotFound() throws Exception {
        when(orderService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrderNotFound() throws Exception {
        doThrow(new OrderNotFoundException(999L)).when(orderService).deleteById(999L);
        mockMvc.perform(delete("/orders/999"))
            .andExpect(status().isNotFound());
    }
}
