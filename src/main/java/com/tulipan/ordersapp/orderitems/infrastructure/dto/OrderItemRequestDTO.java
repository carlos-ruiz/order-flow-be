package com.tulipan.ordersapp.orderitems.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private Integer quantity;
    private Long customerId;
    private Long sellerId;
    private String product;
    private BigDecimal price;
    private BigDecimal tax;
    private Long orderId;
    private Long statusId;
}
