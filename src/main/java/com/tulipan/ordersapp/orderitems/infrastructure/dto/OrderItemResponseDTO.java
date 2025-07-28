package com.tulipan.ordersapp.orderitems.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private Integer quantity;
    private Long customerId;
    private Long sellerId;
    private Long productId;
    private BigDecimal price;
    private Long orderId;
    private Long statusId;
}
