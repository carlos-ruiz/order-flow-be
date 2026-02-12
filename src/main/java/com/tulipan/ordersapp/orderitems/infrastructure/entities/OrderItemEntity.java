package com.tulipan.ordersapp.orderitems.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tulipan.ordersapp.BaseEntity;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;
import com.tulipan.ordersapp.orderitems.infrastructure.listener.OrderItemEntityListener;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "order_items")
@EntityListeners(OrderItemEntityListener.class)
@NamedQuery(name = "OrderItemEntity.findAllBySeller", query = "FROM OrderItemEntity oi WHERE oi.seller.id = :sellerId")
@NamedQuery(name = "OrderItemEntity.findAllByOrder", query = "FROM OrderItemEntity oi WHERE oi.order.id = :orderId")
@NamedQuery(name = "OrderItemEntity.findAllByCustomer", query = "FROM OrderItemEntity oi WHERE oi.customer.id = :customerId")
public class OrderItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;

    private String note;

    @NotNull
    private String product;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;
}
