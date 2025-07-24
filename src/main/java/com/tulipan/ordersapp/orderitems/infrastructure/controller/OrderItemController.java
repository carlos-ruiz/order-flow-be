package com.tulipan.ordersapp.orderitems.infrastructure.controller;

import com.tulipan.ordersapp.orderitems.application.OrderItemService;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.dto.OrderItemRequestDTO;
import com.tulipan.ordersapp.orderitems.infrastructure.dto.OrderItemResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@RequestBody OrderItemRequestDTO orderItemRequestDTO) {
        OrderItem savedOrderItem = orderItemService.save(orderItemRequestDTO.getQuantity(), orderItemRequestDTO.getCustomerId(), orderItemRequestDTO.getSellerId(), orderItemRequestDTO.getProductId(), orderItemRequestDTO.getPrice(), orderItemRequestDTO.getOrderId(), orderItemRequestDTO.getStatusId());
        OrderItemResponseDTO responseDTO = OrderItemResponseDTO.builder()
            .id(savedOrderItem.getId())
            .quantity(savedOrderItem.getQuantity())
            .price(savedOrderItem.getPrice())
            .customerId(savedOrderItem.getCustomer().getId())
            .sellerId(savedOrderItem.getSeller().getId())
            .productId(savedOrderItem.getProduct().getId())
            .orderId(savedOrderItem.getOrder().getId())
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
