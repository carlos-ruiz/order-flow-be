package com.tulipan.ordersapp.orderitems.infrastructure.converters;

import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.customers.infrastructure.entities.CustomerEntity;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.infrastructure.entities.ProductEntity;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderItemConverterTest {

    private static OrderItem createOrderItem() {
        OrderItem item = new OrderItem();
        item.setPrice(new BigDecimal(100));
        item.setQuantity(2);
        item.setId(1L);
        item.setSeller(new Seller(1L, "Seller name", "Seller Last Name", "Seller Address", "Seller phone", "seller@email.com", true));
        item.setOrder(new Order(1L, LocalDateTime.now(), new BigDecimal(10), null));
        item.setProduct(new Product(1L, "Product Name", new BigDecimal(100), "Red", "Small", new BigDecimal(100), "Description"));
        item.setCustomer(new Customer(1L, "Customer Name", "Customer Last Name", "customer@email.com", "Customer phone", "Customer address", "Customer note", true));
        return item;
    }

    @Test
    void toModel() {
        OrderItemEntity entity = createOrderItemEntity();
        OrderItem item = OrderItemConverter.toModel(entity);
        assertNotNull(item);
        assertEntityToModelMapping(entity, item);
    }

    private OrderItemEntity createOrderItemEntity() {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setPrice(new BigDecimal(100));
        entity.setQuantity(2);
        entity.setId(1L);
        entity.setSeller(new SellerEntity(1L, "Seller Name", "Seller Lastname", "Seller Address", "Seller Phone", "email@email.com", true));
        entity.setOrder(new OrderEntity(1L, LocalDateTime.now(), new BigDecimal(10), null));
        entity.setCustomer(new CustomerEntity(1L, "Customer Name", "Customer Lastname", "mail@mail.com", "Customer phone", "Customer address", "Customer note", true));
        entity.setProduct(new ProductEntity(1L, "Product Name", new BigDecimal(100), "Red", "Small", new BigDecimal(100), "Description"));
        return entity;
    }

    private void assertEntityToModelMapping(OrderItemEntity entity, OrderItem item) {
        assertNotNull(item.getProduct());
        assertNotNull(item.getSeller());
        assertNotNull(item.getCustomer());
        assertNotNull(item.getOrder());

        assertEquals(entity.getProduct().getName(), item.getProduct().getName());
        assertEquals(entity.getSeller().getName(), item.getSeller().getName());
        assertEquals(entity.getCustomer().getName(), item.getCustomer().getName());
        assertEquals(entity.getCustomer().getLastName(), item.getCustomer().getLastName());
        assertEquals(entity.getCustomer().getNote(), item.getCustomer().getNote());
        assertEquals(entity.getCustomer().getAddress(), item.getCustomer().getAddress());
        assertEquals(entity.getCustomer().getPhone(), item.getCustomer().getPhone());
        assertEquals(entity.getSeller().getPhone(), item.getSeller().getPhone());
        assertEquals(entity.getSeller().getAddress(), item.getSeller().getAddress());
        assertEquals(entity.getSeller().getLastName(), item.getSeller().getLastName());
        assertEquals(entity.getProduct().getDescription(), item.getProduct().getDescription());
        assertEquals(entity.getProduct().getColor(), item.getProduct().getColor());
        assertEquals(entity.getProduct().getSize(), item.getProduct().getSize());
        assertEquals(entity.getProduct().getBasePrice(), item.getProduct().getBasePrice());
        assertEquals(entity.getProduct().getFinalPrice(), item.getProduct().getFinalPrice());
        assertEquals(entity.getPrice(), item.getPrice());
        assertEquals(entity.getQuantity(), item.getQuantity());
        assertEquals(entity.getId(), item.getId());
        assertEquals(entity.getProduct().getId(), item.getProduct().getId());
        assertEquals(entity.getSeller().getId(), item.getSeller().getId());
        assertEquals(entity.getCustomer().getId(), item.getCustomer().getId());
        assertEquals(entity.getOrder().getId(), item.getOrder().getId());
    }

    @Test
    void toEntity() {
        OrderItem item = createOrderItem();
        OrderItemEntity entity = OrderItemConverter.toEntity(item);
        assertNotNull(entity);
        assertModelToEntityMapping(item, entity);
    }

    private void assertModelToEntityMapping(OrderItem item, OrderItemEntity entity) {
        assertNotNull(entity.getProduct());
        assertNotNull(entity.getSeller());
        assertNotNull(entity.getCustomer());
        assertEquals(item.getProduct().getName(), entity.getProduct().getName());
        assertEquals(item.getSeller().getName(), entity.getSeller().getName());
        assertEquals(item.getCustomer().getName(), entity.getCustomer().getName());
        assertEquals(item.getCustomer().getLastName(), entity.getCustomer().getLastName());
        assertEquals(item.getCustomer().getNote(), entity.getCustomer().getNote());
        assertEquals(item.getCustomer().getAddress(), entity.getCustomer().getAddress());
        assertEquals(item.getCustomer().getPhone(), entity.getCustomer().getPhone());
        assertEquals(item.getSeller().getPhone(), entity.getSeller().getPhone());
        assertEquals(item.getSeller().getAddress(), entity.getSeller().getAddress());
        assertEquals(item.getSeller().getLastName(), entity.getSeller().getLastName());
        assertEquals(item.getProduct().getDescription(), entity.getProduct().getDescription());
        assertEquals(item.getProduct().getColor(), entity.getProduct().getColor());
        assertEquals(item.getProduct().getSize(), entity.getProduct().getSize());
        assertEquals(item.getProduct().getBasePrice(), entity.getProduct().getBasePrice());
        assertEquals(item.getProduct().getFinalPrice(), entity.getProduct().getFinalPrice());
        assertEquals(item.getPrice(), entity.getPrice());
        assertEquals(item.getQuantity(), entity.getQuantity());
        assertEquals(item.getId(), entity.getId());
        assertEquals(item.getProduct().getId(), entity.getProduct().getId());
        assertEquals(item.getSeller().getId(), entity.getSeller().getId());
        assertEquals(item.getCustomer().getId(), entity.getCustomer().getId());
        assertEquals(item.getOrder().getId(), entity.getOrder().getId());

    }
}
