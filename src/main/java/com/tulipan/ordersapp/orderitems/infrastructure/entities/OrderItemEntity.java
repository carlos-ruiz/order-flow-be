package com.tulipan.ordersapp.orderitems.infrastructure.entities;

import com.tulipan.ordersapp.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "order_items")
public class OrderItemEntity extends BaseEntity {
    @Id
    private Long id;

    private String productName;

    private Integer quantity;

    private Float price;

    private Float discount;

    private String note;

    private Boolean active;
}
