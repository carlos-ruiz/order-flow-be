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

    @Test
    void toModel() {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setPrice(new BigDecimal(100));
        entity.setQuantity(2);
        entity.setId(1L);
        entity.setSeller(new SellerEntity(1L, "Seller Name", "Seller Lastname", "Seller Address", "Seller Phone", "email@email.com"));
        entity.setOrder(new OrderEntity(1L, LocalDateTime.now(), new BigDecimal(10), null, null));
        entity.setCustomer(new CustomerEntity(1L, "Customer Name", "Customer Lastname", "mail@mail.com", "Customer phone", "Customer address", "Customer note"));
        entity.setProduct(new ProductEntity(1L, "Product Name", new BigDecimal(100), "Red", "Small", new BigDecimal(100), "Description"));

        OrderItem item = OrderItemConverter.toModel(entity);

        assertNotNull(item);
        assertNotNull(item.getProduct());
        assertNotNull(item.getSeller());
        assertNotNull(item.getCustomer());
        assertNotNull(item.getOrder());
        assertEquals("Product Name", item.getProduct().getName());
        assertEquals("Seller Name", item.getSeller().getName());
        assertEquals("Customer Name", item.getCustomer().getName());
        assertEquals("Customer Lastname", item.getCustomer().getLastName());
        assertEquals("Customer note", item.getCustomer().getNote());
        assertEquals("Customer address", item.getCustomer().getAddress());
        assertEquals("Customer phone", item.getCustomer().getPhone());
        assertEquals("Seller Phone", item.getSeller().getPhone());
        assertEquals("Seller Address", item.getSeller().getAddress());
        assertEquals("Seller Lastname", item.getSeller().getLastName());
        assertEquals("Seller Name", item.getSeller().getName());
        assertEquals("Description", item.getProduct().getDescription());
        assertEquals("Red", item.getProduct().getColor());
        assertEquals("Small", item.getProduct().getSize());
        assertEquals(new BigDecimal(100), item.getProduct().getBasePrice());
        assertEquals(new BigDecimal(100), item.getProduct().getFinalPrice());
        assertEquals(new BigDecimal(100), item.getPrice());
        assertEquals(2, item.getQuantity());
        assertEquals(1L, item.getId());
        assertEquals(1L, item.getProduct().getId());
        assertEquals(1L, item.getSeller().getId());
        assertEquals(1L, item.getCustomer().getId());
        assertEquals(1L, item.getOrder().getId());
    }

    @Test
    void toEntity() {
        OrderItem item = new OrderItem();
        item.setPrice(new BigDecimal(100));
        item.setQuantity(2);
        item.setId(1L);
        item.setSeller(new Seller(1L, "Seller name", "Seller Last Name", "Seller Address", "Seller phone", "seller@email.com"));
        item.setOrder(new Order(1L, LocalDateTime.now(), new BigDecimal(10), null, null));
        item.setProduct(new Product(1L, "Product Name", new BigDecimal(100), "Red", "Small", new BigDecimal(100), "Description"));
        item.setCustomer(new Customer(1L, "Customer Name", "Customer Last Name", "customer@email.com", "Customer phone", "Customer address", "Customer note"));

        OrderItemEntity entity = OrderItemConverter.toEntity(item);
        assertNotNull(entity);
        assertNotNull(entity.getProduct());
        assertNotNull(entity.getSeller());
        assertNotNull(entity.getCustomer());
        assertEquals("Product Name", entity.getProduct().getName());
        assertEquals("Seller name", entity.getSeller().getName());
        assertEquals("Customer Name", entity.getCustomer().getName());
        assertEquals("Customer Last Name", entity.getCustomer().getLastName());
        assertEquals("Customer note", entity.getCustomer().getNote());
        assertEquals("Customer address", entity.getCustomer().getAddress());
        assertEquals("Customer phone", entity.getCustomer().getPhone());
        assertEquals("Seller phone", entity.getSeller().getPhone());
        assertEquals("Seller Address", entity.getSeller().getAddress());
        assertEquals("Seller Last Name", entity.getSeller().getLastName());
        assertEquals("Seller name", entity.getSeller().getName());
        assertEquals("Description", entity.getProduct().getDescription());
        assertEquals("Red", entity.getProduct().getColor());
        assertEquals("Small", entity.getProduct().getSize());
        assertEquals(new BigDecimal(100), entity.getProduct().getBasePrice());
        assertEquals(new BigDecimal(100), entity.getProduct().getFinalPrice());
        assertEquals(new BigDecimal(100), entity.getPrice());
        assertEquals(2, entity.getQuantity());
        assertEquals(1L, entity.getId());
        assertEquals(1L, entity.getProduct().getId());
        assertEquals(1L, entity.getSeller().getId());
        assertEquals(1L, entity.getCustomer().getId());
        assertEquals(1L, entity.getOrder().getId());
    }
}
