package com.tulipan.ordersapp.orderitems.infrastructure.dto;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
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
    private String customerName;
    private Long sellerId;
    private String sellerName;
    private String product;
    private BigDecimal price;
    private BigDecimal tax;
    private Long orderId;
    private Long statusId;

    public static OrderItemResponseDTO fromOrderItem(OrderItem orderItem) {
        return OrderItemResponseDTO.builder()
            .id(orderItem.getId())
            .quantity(orderItem.getQuantity())
            .customerId(orderItem.getCustomer() != null ? orderItem.getCustomer().getId() : null)
            .customerName(orderItem.getCustomer() != null ? orderItem.getCustomer().getName() : null)
            .sellerId(orderItem.getSeller() != null ? orderItem.getSeller().getId() : null)
            .sellerName(orderItem.getSeller() != null ? orderItem.getSeller().getName() : null)
            .product(orderItem.getProduct())
            .price(orderItem.getPrice())
            .tax(orderItem.getTax())
            .orderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null)
            .statusId(orderItem.getStatus() != null ? orderItem.getStatus().getId() : null)
            .build();
    }
}
