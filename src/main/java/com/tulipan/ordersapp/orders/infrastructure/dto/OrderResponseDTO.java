package com.tulipan.ordersapp.orders.infrastructure.dto;

import com.tulipan.ordersapp.orderitems.infrastructure.dto.OrderItemResponseDTO;
import com.tulipan.ordersapp.orders.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private BigDecimal discount;
    private Long platformId;
    private String platformName;
    private BigDecimal totalAmount;
    private Long statusId;
    private String statusName;
    private List<OrderItemResponseDTO> items;

    public static OrderResponseDTO fromOrder(Order order) {
        return OrderResponseDTO.builder()
            .id(order.getId())
            .dateTime(order.getDateTime())
            .discount(order.getDiscount())
            .platformId(order.getPlatform() != null ? order.getPlatform().getId() : null)
            .platformName(order.getPlatform() != null ? order.getPlatform().getName() : null)
            .totalAmount(order.getTotalAmount())
            .statusId(order.getStatus() != null ? order.getStatus().getId() : null)
            .statusName(order.getStatus() != null ? order.getStatus().getName() : null)
            .items(order.getOrderItems() != null ? order.getOrderItems().stream()
                .map(OrderItemResponseDTO::fromOrderItem)
                .toList() : List.of())
            .build();
    }

}
