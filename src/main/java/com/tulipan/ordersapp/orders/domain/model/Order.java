package com.tulipan.ordersapp.orders.domain.model;

import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;

    private LocalDateTime dateTime;
    private BigDecimal discount;

    private Platform platform;

    private List<OrderItem> orderItems;
}
