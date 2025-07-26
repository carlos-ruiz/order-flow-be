package com.tulipan.ordersapp.orders.infrastructure.controller;

import com.tulipan.ordersapp.orders.application.OrderService;
import com.tulipan.ordersapp.orders.domain.exceptions.OrderNotFoundException;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.dto.OrderRequestDTO;
import com.tulipan.ordersapp.orders.infrastructure.dto.OrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.getPlatformId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Order savedOrder = orderService.save(orderRequestDTO.getDateTime(), orderRequestDTO.getDiscount(), orderRequestDTO.getPlatformId());
        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
            .id(savedOrder.getId())
            .dateTime(savedOrder.getDateTime())
            .discount(savedOrder.getDiscount())
            .platformId(savedOrder.getPlatform().getId())
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        List<OrderResponseDTO> responseDTOs = orders.stream()
            .map(order -> OrderResponseDTO.builder()
                .id(order.getId())
                .dateTime(order.getDateTime())
                .discount(order.getDiscount())
                .platformId(order.getPlatform().getId())
                .build())
            .toList();
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
            .map(order -> {
                OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                    .id(order.getId())
                    .dateTime(order.getDateTime())
                    .discount(order.getDiscount())
                    .platformId(order.getPlatform().getId())
                    .build();
                return ResponseEntity.ok(responseDTO);
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteById(id);
        } catch (OrderNotFoundException e) {
            System.err.println("Order with ID " + id + " not found for deletion.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
