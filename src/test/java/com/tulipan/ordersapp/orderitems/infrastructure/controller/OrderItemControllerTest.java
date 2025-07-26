package com.tulipan.ordersapp.orderitems.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.orderitems.application.OrderItemService;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.dto.OrderItemResponseDTO;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.status.domain.model.Status;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderItemService orderItemService;

    private Customer customer;
    private Seller seller;
    private Product product;
    private Status status;
    private Order order;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderItemController(orderItemService)).build();
        customer = new Customer(1L, "John", "Doe", "mail@mail.com", "1234567890", "123 Main St", "Note");
        seller = new Seller(2L, "Jane", "Doe", "202 second St", "9876543210", "mail@mail.com");
        product = new Product(3L, "Shampoo", BigDecimal.valueOf(50.00), "N/A", "N/A", BigDecimal.valueOf(50.00), "Shampoo for all hair types");
        status = new Status(1L, "Pending", true);
        Platform platform = new Platform(1L, "Amazon", BigDecimal.ZERO, BigDecimal.valueOf(10.00), true);
        order = new Order(4L, LocalDateTime.now(), BigDecimal.ZERO, platform);
    }

    @Test
    void createOrderItem() throws Exception {
        OrderItem orderItem = new OrderItem(null, 5, customer, seller, product, new BigDecimal("100.00"), order, status);
        OrderItem savedOrderItem = new OrderItem(1L, 5, customer, seller, product, new BigDecimal("100.00"), order, status);
        when(orderItemService.save(
            orderItem.getQuantity(),
            orderItem.getCustomer().getId(),
            orderItem.getSeller().getId(),
            orderItem.getProduct().getId(),
            orderItem.getPrice(),
            orderItem.getOrder().getId(),
            orderItem.getStatus().getId()
        )).thenReturn(savedOrderItem);
        String requestBody = "{\"quantity\":5, \"customerId\":1, \"sellerId\":2, \"productId\":3, \"price\":100.00, \"orderId\":4, \"statusId\":1}";
        String responseContent = mockMvc.perform(post("/order-items")
                .contentType("application/json")
                .content(requestBody))
            .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        OrderItemResponseDTO responseDTO = objectMapper.readValue(responseContent, OrderItemResponseDTO.class);

        assertEquals(savedOrderItem.getId(), responseDTO.getId());
        assertEquals(savedOrderItem.getQuantity(), responseDTO.getQuantity());
        assertEquals(savedOrderItem.getPrice(), responseDTO.getPrice());
        assertEquals(savedOrderItem.getCustomer().getId(), responseDTO.getCustomerId());
        assertEquals(savedOrderItem.getSeller().getId(), responseDTO.getSellerId());
        assertEquals(savedOrderItem.getProduct().getId(), responseDTO.getProductId());
        assertEquals(savedOrderItem.getOrder().getId(), responseDTO.getOrderId());
    }

    @Test
    void findByOrderId() throws Exception {
        OrderItem orderItem = new OrderItem(4L, 5, customer, seller, product, new BigDecimal("100.00"), order, status);
        List<OrderItem> orderItems = List.of(orderItem);
        when(orderItemService.findByOrderId(order.getId())).thenReturn(orderItems);

        String responseContent = mockMvc.perform(get("/order-items/order/4"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderItemResponseDTO> itemResponseDTOS = objectMapper.readValue(responseContent, new TypeReference<List<OrderItemResponseDTO>>() {
        });
        assertEquals(1, itemResponseDTOS.size());
        assertEquals(orderItem.getId(), itemResponseDTOS.getFirst().getId());
        assertEquals(orderItem.getQuantity(), itemResponseDTOS.getFirst().getQuantity());
        assertEquals(orderItem.getPrice(), itemResponseDTOS.getFirst().getPrice());
        assertEquals(orderItem.getCustomer().getId(), itemResponseDTOS.getFirst().getCustomerId());
        assertEquals(orderItem.getSeller().getId(), itemResponseDTOS.getFirst().getSellerId());
        assertEquals(orderItem.getProduct().getId(), itemResponseDTOS.getFirst().getProductId());
        assertEquals(orderItem.getOrder().getId(), itemResponseDTOS.getFirst().getOrderId());
    }
}
