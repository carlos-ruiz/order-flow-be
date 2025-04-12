package com.tulipan.ordersapp.orderitems.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.products.infrastructure.entities.ProductEntity;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "order_items")
@NamedQuery(name = "OrderItemEntity.findAllBySeller", query = "FROM OrderItemEntity oi WHERE oi.seller.id = :sellerId")
@NamedQuery(name = "OrderItemEntity.findAllByOrder", query = "FROM OrderItemEntity oi WHERE oi.order.id = :orderId")
@NamedQuery(name = "OrderItemEntity.findAllByCustomer", query = "FROM OrderItemEntity oi WHERE oi.customer.id = :customerId")
public class OrderItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal price;

    private String note;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;
}
