package com.tulipan.ordersapp.orders.application;

import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.platforms.application.PlatformService;
import com.tulipan.ordersapp.platforms.domain.exceptions.PlatformNotFoundException;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private StatusService statusService;

    private Platform platform;
    private Status status;
    private Order order;
    private Long statusId;

    @BeforeEach
    void setUp() {
        log.info("Setting up OrderServiceImplTest");
        platform = Platform.builder()
            .name("Amazon")
            .customerFee(BigDecimal.ZERO)
            .sellerCommission(BigDecimal.TEN)
            .active(true)
            .build();
        platform = platformService.save(platform);

        status = new Status(null, "Pending", true);
        status = statusService.save(status);
        statusId = status.getId();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        order = Order.builder()
            .dateTime(LocalDateTime.parse("2025-07-24T10:00:00", formatter))
            .platform(platform)
            .discount(BigDecimal.ZERO)
            .status(status)
            .build();
    }

    @Test
    void testSave() {
        Order savedOrder = orderService.save(order);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertEquals(order.getDateTime(), savedOrder.getDateTime());
        assertEquals(platform.getName(), savedOrder.getPlatform().getName());
    }

    @Test
    void testSaveWithPlatformId() {
        Order savedOrder = orderService.save(order.getDateTime(), order.getDiscount(), platform.getId(), status.getId());

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertEquals(order.getDateTime(), savedOrder.getDateTime());
        assertEquals(platform.getName(), savedOrder.getPlatform().getName());
    }

    @Test
    void testSaveWithNullPlatformId() {
        LocalDateTime dateTime = order.getDateTime();
        BigDecimal discount = order.getDiscount();
        assertThrows(IllegalArgumentException.class, () -> orderService.save(dateTime, discount, null, statusId), "Platform ID cannot be null");
    }

    @Test
    void testSaveWithNonExistentPlatform() {
        Long nonExistentPlatformId = 999L; // Assuming this ID does not exist
        LocalDateTime dateTime = order.getDateTime();
        BigDecimal discount = order.getDiscount();
        assertThrows(PlatformNotFoundException.class, () -> orderService.save(dateTime, discount, nonExistentPlatformId, statusId), "Platform with ID " + nonExistentPlatformId + " should not exist");
    }

    @Test
    void testUpdate() {
        Order savedOrder = orderService.save(order);
        savedOrder.setDiscount(BigDecimal.TEN);

        Order updatedOrder = orderService.update(savedOrder);

        assertNotNull(updatedOrder);
        assertEquals(savedOrder.getId(), updatedOrder.getId());
        assertEquals(BigDecimal.TEN, updatedOrder.getDiscount());
        assertEquals(savedOrder.getDateTime(), updatedOrder.getDateTime());
    }

    @Test
    void testDelete() {
        Order savedOrder = orderService.save(order);
        orderService.delete(savedOrder);

        Order foundOrder = orderService.findById(savedOrder.getId()).orElse(null);
        assertNull(foundOrder, "Order should be deleted and not found");
    }

    @Test
    void deleteById() {
        Order savedOrder = orderService.save(order);
        Long orderId = savedOrder.getId();
        orderService.deleteById(orderId);

        Order foundOrder = orderService.findById(orderId).orElse(null);
        assertNull(foundOrder, "Order should be deleted and not found");
    }

    @Test
    void findById() {
        Order savedOrder = orderService.save(order);
        Long orderId = savedOrder.getId();

        Order foundOrder = orderService.findById(orderId).orElse(null);
        assertNotNull(foundOrder, "Order should be found");
        assertEquals(savedOrder.getId(), foundOrder.getId());
        assertEquals(savedOrder.getDateTime(), foundOrder.getDateTime());
        assertEquals(platform.getName(), foundOrder.getPlatform().getName());
    }

    @Test
    void findAll() {
        orderService.save(order);
        Order anotherOrder = Order.builder()
            .dateTime(LocalDateTime.now())
            .platform(platform)
            .status(status)
            .discount(BigDecimal.ZERO)
            .build();
        orderService.save(anotherOrder);

        var orders = orderService.findAll();
        assertFalse(orders.isEmpty(), "Orders list should not be empty");
        assertTrue(orders.stream().anyMatch(o -> o.getDateTime().equals(order.getDateTime())));
        assertTrue(orders.stream().anyMatch(o -> o.getDateTime().equals(anotherOrder.getDateTime())));
    }
}
