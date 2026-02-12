package com.tulipan.ordersapp.orders.infrastructure.repository;

import com.tulipan.ordersapp.orders.domain.exceptions.OrderNotFoundException;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.orders.infrastructure.entities.OrderEntity;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.platforms.infrastructure.entities.PlatformEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryAdapterTest {
    @Mock
    private JpaOrderRepository jpaOrderRepository;
    @InjectMocks
    private OrderRepositoryAdapter orderRepositoryAdapter;

    @Test
    void testToModel() {
        LocalDateTime now = LocalDateTime.now();
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        OrderEntity orderEntity = OrderEntity.builder()
            .id(1L)
            .dateTime(now)
            .discount(BigDecimal.ZERO)
            .platform(platformEntity)
            .build();
        Order order = orderRepositoryAdapter.toModel(orderEntity);
        assertEquals(1L, order.getId());
        assertEquals(now, order.getDateTime());
        assertEquals(BigDecimal.ZERO, order.getDiscount());
        assertEquals("Amazon", order.getPlatform().getName());
        assertEquals(BigDecimal.ZERO, order.getPlatform().getCustomerFee());
        assertEquals(BigDecimal.TEN, order.getPlatform().getSellerCommission());
        assertEquals(true, order.getPlatform().getActive());
    }

    @Test
    void testToModelWithNullEntity() {
        Order order = orderRepositoryAdapter.toModel(null);
        assertNull(order);
    }

    @Test
    void testToEntity() {
        LocalDateTime now = LocalDateTime.now();
        Platform platform = Platform.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        Order order = new Order(1L, now, BigDecimal.ZERO, platform, BigDecimal.ZERO);
        OrderEntity orderEntity = orderRepositoryAdapter.toEntity(order);
        assertEquals(1L, orderEntity.getId());
        assertEquals(now, orderEntity.getDateTime());
        assertEquals(BigDecimal.ZERO, orderEntity.getDiscount());
        assertEquals("Amazon", orderEntity.getPlatform().getName());
        assertEquals(BigDecimal.ZERO, orderEntity.getPlatform().getCustomerFee());
        assertEquals(BigDecimal.TEN, orderEntity.getPlatform().getSellerCommission());
        assertEquals(true, orderEntity.getPlatform().getActive());
    }

    @Test
    void testToEntityWithNullOrder() {
        OrderEntity orderEntity = orderRepositoryAdapter.toEntity(null);
        assertNull(orderEntity);
    }

    @Test
    void testToEntityWithNullPlatform() {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(1L, now, BigDecimal.ZERO, null, BigDecimal.ZERO);
        OrderEntity orderEntity = orderRepositoryAdapter.toEntity(order);
        assertNull(orderEntity.getPlatform());
    }

    @Test
    void testFindById_whenExists() {
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        OrderEntity orderEntity = OrderEntity.builder()
            .id(id)
            .dateTime(now)
            .discount(BigDecimal.ZERO)
            .platform(platformEntity)
            .build();

        when(jpaOrderRepository.findById(id)).thenReturn(Optional.of(orderEntity));

        Order order = orderRepositoryAdapter.findById(id).orElse(null);

        assertNotNull(order);
        assertEquals(id, order.getId());
        assertEquals(now, order.getDateTime());
        assertEquals(BigDecimal.ZERO, order.getDiscount());
        assertEquals("Amazon", order.getPlatform().getName());
    }

    @Test
    void testFindById_whenNotExists() {
        Long id = 1L;

        when(jpaOrderRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Order> order = orderRepositoryAdapter.findById(id);

        assertFalse(order.isPresent());
    }

    @Test
    void testSave() {
        LocalDateTime now = LocalDateTime.now();
        Platform platform = Platform.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        Order order = new Order(1L, now, BigDecimal.ZERO, platform, BigDecimal.ZERO);
        OrderEntity orderEntity = orderRepositoryAdapter.toEntity(order);

        when(jpaOrderRepository.save(orderEntity)).thenReturn(orderEntity);

        Order savedOrder = orderRepositoryAdapter.save(order);

        assertNotNull(savedOrder);
        assertEquals(1L, savedOrder.getId());
        assertEquals(now, savedOrder.getDateTime());
        assertEquals(BigDecimal.ZERO, savedOrder.getDiscount());
        assertEquals("Amazon", savedOrder.getPlatform().getName());
    }

    @Test
    void testUpdate() {
        LocalDateTime now = LocalDateTime.now();
        Platform platform = Platform.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        Order order = new Order(1L, now, BigDecimal.ZERO, platform, BigDecimal.ZERO);
        OrderEntity orderEntity = orderRepositoryAdapter.toEntity(order);

        when(jpaOrderRepository.save(orderEntity)).thenReturn(orderEntity);

        Order updatedOrder = orderRepositoryAdapter.update(order);

        assertNotNull(updatedOrder);
        assertEquals(1L, updatedOrder.getId());
        assertEquals(now, updatedOrder.getDateTime());
        assertEquals(BigDecimal.ZERO, updatedOrder.getDiscount());
        assertEquals("Amazon", updatedOrder.getPlatform().getName());
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        when(jpaOrderRepository.existsById(id)).thenReturn(true);
        orderRepositoryAdapter.deleteById(id);
        verify(jpaOrderRepository).deleteById(id);
    }

    @Test
    void testDeleteById_whenNotExists() {
        Long id = 1L;
        when(jpaOrderRepository.existsById(id)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderRepositoryAdapter.deleteById(id));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        Order order = new Order(id, LocalDateTime.now(), BigDecimal.ZERO, null, BigDecimal.ZERO);
        when(jpaOrderRepository.existsById(id)).thenReturn(true);

        orderRepositoryAdapter.delete(order);

        verify(jpaOrderRepository).delete(orderRepositoryAdapter.toEntity(order));
    }

    @Test
    void testDelete_whenNotExists() {
        Long id = 1L;
        Order order = new Order(id, LocalDateTime.now(), BigDecimal.ZERO, null, BigDecimal.ZERO);
        when(jpaOrderRepository.existsById(id)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderRepositoryAdapter.delete(order));
    }

    @Test
    void testFindAll() {
        LocalDateTime now = LocalDateTime.now();
        PlatformEntity platformEntity = PlatformEntity.builder()
            .id(1L)
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        OrderEntity orderEntity = OrderEntity.builder()
            .id(1L)
            .dateTime(now)
            .discount(BigDecimal.ZERO)
            .platform(platformEntity)
            .build();

        when(jpaOrderRepository.findAll()).thenReturn(List.of(orderEntity));

        List<Order> orders = orderRepositoryAdapter.findAll();

        assertEquals(1, orders.size());
        assertEquals(1L, orders.getFirst().getId());
        assertEquals(now, orders.getFirst().getDateTime());
        assertEquals(BigDecimal.ZERO, orders.getFirst().getDiscount());
        assertEquals("Amazon", orders.getFirst().getPlatform().getName());
    }

}
