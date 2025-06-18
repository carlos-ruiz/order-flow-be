package com.tulipan.ordersapp.orderitems.infrastructure.repository;

import com.tulipan.ordersapp.orderitems.domain.exceptions.OrderItemNotFoundException;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.infrastructure.converters.OrderItemConverter;
import com.tulipan.ordersapp.orderitems.infrastructure.entities.OrderItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemRepositoryAdapterTest {

    @Mock
    private JpaOrderItemRepository jpaOrderItemRepository;

    @InjectMocks
    private OrderItemRepositoryAdapter orderItemRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savesOrderItemSuccessfully() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(new BigDecimal("100.0"));
        orderItem.setQuantity(2);

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(1L);
        orderItemEntity.setPrice(new BigDecimal("100.0"));
        orderItemEntity.setQuantity(2);

        when(jpaOrderItemRepository.save(any(OrderItemEntity.class))).thenReturn(orderItemEntity);

        OrderItem savedOrderItem = orderItemRepositoryAdapter.save(orderItem);

        assertNotNull(savedOrderItem);
        assertEquals(1L, savedOrderItem.getId());
        assertEquals(new BigDecimal("100.0"), savedOrderItem.getPrice());
        assertEquals(2, savedOrderItem.getQuantity());
    }

    @Test
    void throwsExceptionWhenUpdatingNonExistentOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);

        when(jpaOrderItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderItemNotFoundException.class, () -> orderItemRepositoryAdapter.update(orderItem));
    }

    @Test
    void updatesOrderItemSuccessfully() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(new BigDecimal("100.0"));
        orderItem.setQuantity(2);

        OrderItemEntity orderItemEntity = OrderItemConverter.toEntity(orderItem);

        when(jpaOrderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItemEntity));
        when(jpaOrderItemRepository.save(any(OrderItemEntity.class))).thenReturn(orderItemEntity);

        OrderItem updatedOrderItem = orderItemRepositoryAdapter.update(orderItem);

        assertNotNull(updatedOrderItem);
        assertEquals(1L, updatedOrderItem.getId());
        assertEquals(new BigDecimal("100.0"), updatedOrderItem.getPrice());
        assertEquals(2, updatedOrderItem.getQuantity());
    }

    @Test
    void deletesOrderItemByIdSuccessfully() {
        Long id = 1L;

        doNothing().when(jpaOrderItemRepository).deleteById(id);

        orderItemRepositoryAdapter.deleteById(id);

        verify(jpaOrderItemRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteOrderItemSuccessfully() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);

        doNothing().when(jpaOrderItemRepository).deleteById(orderItem.getId());

        orderItemRepositoryAdapter.delete(orderItem);

        verify(jpaOrderItemRepository, times(1)).deleteById(orderItem.getId());
    }

    @Test
    void findsOrderItemByIdSuccessfully() {
        Long id = 1L;
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(id);

        when(jpaOrderItemRepository.findById(id)).thenReturn(Optional.of(orderItemEntity));

        Optional<OrderItem> result = orderItemRepositoryAdapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void returnsEmptyListWhenNoOrderItemsFound() {
        when(jpaOrderItemRepository.findAll()).thenReturn(List.of());

        List<OrderItem> result = orderItemRepositoryAdapter.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findsOrderItemsByOrderIdSuccessfully() {
        Long orderId = 1L;
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(1L);

        when(jpaOrderItemRepository.findAllByOrder(orderId)).thenReturn(List.of(orderItemEntity));

        List<OrderItem> result = orderItemRepositoryAdapter.findByOrderId(orderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void findsOrderItemsBySellerIdSuccessfully() {
        Long sellerId = 1L;
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(1L);

        when(jpaOrderItemRepository.findAllBySeller(sellerId)).thenReturn(List.of(orderItemEntity));

        List<OrderItem> result = orderItemRepositoryAdapter.findBySellerId(sellerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void findsOrderItemsByCustomerIdSuccessfully() {
        Long customerId = 1L;
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(1L);

        when(jpaOrderItemRepository.findAllByCustomer(customerId)).thenReturn(List.of(orderItemEntity));

        List<OrderItem> result = orderItemRepositoryAdapter.findByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }
}
