package com.tulipan.ordersapp.orderitems.application;

import com.tulipan.ordersapp.customers.application.CustomerService;
import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.orderitems.domain.exceptions.OrderItemNotFoundException;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orders.application.OrderService;
import com.tulipan.ordersapp.orders.domain.exceptions.OrderNotFoundException;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.platforms.application.PlatformService;
import com.tulipan.ordersapp.platforms.domain.model.Platform;
import com.tulipan.ordersapp.products.application.ProductService;
import com.tulipan.ordersapp.products.domain.ProductNotFoundException;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.sellers.application.SellerService;
import com.tulipan.ordersapp.sellers.domain.exceptions.SellerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.exceptions.StatusNotFoundException;
import com.tulipan.ordersapp.status.domain.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class OrderItemServiceImplTest {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private StatusService statusService;

    private Customer customer;
    private Seller seller;
    private Product product;
    private Order order;
    private Status status;

    @BeforeEach
    void setUp() {
        customer = new Customer(null, "John", "Doe", "email@example.com", "1234567890", "123 Main St.", "Note");
        customer = customerService.save(customer);

        seller = new Seller(null, "Richard", "Smith", "302 First Av", "1234567890", "seller@mail.com");
        seller = sellerService.save(seller);

        product = new Product(null, "Product Name", BigDecimal.valueOf(100.00), "Negro", "M", BigDecimal.valueOf(120.00), "Product Description");
        product = productService.save(product);

        Platform platform = new Platform(null, "Amazon", BigDecimal.valueOf(10), BigDecimal.valueOf(20), true);
        platform = platformService.save(platform);
        log.info("Platform saved: {}", platform);

        order = new Order(null, LocalDateTime.now(), BigDecimal.valueOf(10), platform);
        order = orderService.save(order);

        status = new Status(null, "Pending", true);
        status = statusService.save(status);
    }

    @Test
    void findById_shouldReturnOrderItem_whenOrderItemExists() {
        OrderItem newOrderItem = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        OrderItem orderItemFound = orderItemService.findById(newOrderItem.getId()).orElse(null);
        assertNotNull(orderItemFound);
        assertEquals("Pending", orderItemFound.getStatus().getName());
        assertEquals(newOrderItem.getId(), orderItemFound.getId());
        assertEquals(newOrderItem.getQuantity(), orderItemFound.getQuantity());
        assertEquals(newOrderItem.getPrice(), orderItemFound.getPrice());
        assertEquals(customer.getId(), orderItemFound.getCustomer().getId());
        assertEquals(seller.getId(), orderItemFound.getSeller().getId());
        assertEquals(product.getId(), orderItemFound.getProduct().getId());
        assertEquals("John", orderItemFound.getCustomer().getName());
        assertEquals("Richard", orderItemFound.getSeller().getName());
        assertEquals("Product Name", orderItemFound.getProduct().getName());
        assertEquals("Amazon", orderItemFound.getOrder().getPlatform().getName());
    }

    @Test
    void findById_shouldReturnEmpty_whenOrderItemDoesNotExist() {
        Optional<OrderItem> orderItem = orderItemService.findById(999L); // Assuming 999L is a non-existing ID
        assertFalse(orderItem.isPresent());
    }

    @Test
    void save_shouldCreateOrderItemWithValidData() {
        OrderItem newOrderItem = orderItemService.save(3, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(150.00), order.getId(), status.getId());
        assertNotNull(newOrderItem);
        assertNotNull(newOrderItem.getId());
        assertEquals(3, newOrderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(150.00), newOrderItem.getPrice());
        assertEquals(customer.getId(), newOrderItem.getCustomer().getId());
        assertEquals(seller.getId(), newOrderItem.getSeller().getId());
        assertEquals(product.getId(), newOrderItem.getProduct().getId());
        assertEquals(order.getId(), newOrderItem.getOrder().getId());
        assertEquals(status.getId(), newOrderItem.getStatus().getId());
    }

    @Test
    void save_shouldCreateOrderItemWithDefaultStatus_whenStatusNotFound() {
        Status defaultStatus = statusService.findByName("Pending").orElseThrow(() -> new RuntimeException("Default status not found"));
        OrderItem newOrderItem = orderItemService.save(1, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(50.00), order.getId(), null);
        assertNotNull(newOrderItem);
        assertEquals(defaultStatus.getId(), newOrderItem.getStatus().getId());
    }

    @Test
    void save_shouldCreateOrderItemWithObject() {
        OrderItem orderItem = new OrderItem(null, 2, customer, seller, product, BigDecimal.valueOf(100.00), order, status);
        OrderItem savedOrderItem = orderItemService.save(orderItem);
        assertNotNull(savedOrderItem);
        assertNotNull(savedOrderItem.getId());
        assertEquals(orderItem.getQuantity(), savedOrderItem.getQuantity());
        assertEquals(orderItem.getPrice(), savedOrderItem.getPrice());
        assertEquals(orderItem.getCustomer().getId(), savedOrderItem.getCustomer().getId());
        assertEquals(orderItem.getSeller().getId(), savedOrderItem.getSeller().getId());
        assertEquals(orderItem.getProduct().getId(), savedOrderItem.getProduct().getId());
        assertEquals(orderItem.getOrder().getId(), savedOrderItem.getOrder().getId());
        assertEquals(orderItem.getStatus().getId(), savedOrderItem.getStatus().getId());
        assertEquals("John", savedOrderItem.getCustomer().getName());
        assertEquals("Richard", savedOrderItem.getSeller().getName());
        assertEquals("Product Name", savedOrderItem.getProduct().getName());
        assertEquals("Amazon", savedOrderItem.getOrder().getPlatform().getName());
    }

    @Test
    void save_shouldThrowException_whenQuantityIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
            orderItemService.save(0, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(150.00), order.getId(), status.getId())
        );
    }

    @Test
    void save_shouldThrowException_whenQuantityIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
            orderItemService.save(null, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(150.00), order.getId(), status.getId()), "Quantity must be greater than zero");
    }

    @Test
    void save_shouldThrowException_whenPriceIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
            orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.ZERO, order.getId(), status.getId()));
    }

    @Test
    void save_shouldThrowException_whenPriceIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
            orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), null, order.getId(), status.getId()));
    }

    @Test
    void save_shouldThrowException_whenOrderDoesNotExist() {
        assertThrows(OrderNotFoundException.class, () -> {
            orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), 999L, status.getId()); // Assuming 999L is a non-existing order ID
        });
    }

    @Test
    void save_shouldThrowException_whenProductDoesNotExist() {
        assertThrows(ProductNotFoundException.class, () -> {
            orderItemService.save(2, customer.getId(), seller.getId(), 999L, BigDecimal.valueOf(100.00), order.getId(), status.getId()); // Assuming 999L is a non-existing product ID
        });
    }

    @Test
    void save_shouldThrowException_whenCustomerDoesNotExist() {
        assertThrows(CustomerNotFoundException.class, () -> {
            orderItemService.save(2, 999L, seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId()); // Assuming 999L is a non-existing customer ID
        });
    }

    @Test
    void save_shouldThrowException_whenSellerDoesNotExist() {
        assertThrows(SellerNotFoundException.class, () -> {
            orderItemService.save(2, customer.getId(), 999L, product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId()); // Assuming 999L is a non-existing seller ID
        });
    }

    @Test
    void save_shouldThrowException_whenStatusDoesNotExist() {
        assertThrows(StatusNotFoundException.class, () -> {
            orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), 999L); // Assuming 999L is a non-existing status ID
        });
    }

    @Test
    void update_shouldUpdateOrderItem_whenOrderItemExists() {
        OrderItem newOrderItem = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        newOrderItem.setQuantity(5);
        newOrderItem.setPrice(BigDecimal.valueOf(200.00));
        OrderItem updatedOrderItem = orderItemService.update(newOrderItem);
        assertNotNull(updatedOrderItem);
        assertEquals(5, updatedOrderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(200.00), updatedOrderItem.getPrice());
    }

    @Test
    void delete_shouldRemoveOrderItem_whenOrderItemExists() {
        OrderItem newOrderItem = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        orderItemService.delete(newOrderItem);
        assertFalse(orderItemService.findById(newOrderItem.getId()).isPresent());
    }

    @Test
    void delete_shouldThrowException_whenOrderItemDoesNotExist() {
        OrderItem nonExistingOrderItem = new OrderItem(999L, 2, customer, seller, product, BigDecimal.valueOf(100.00), order, status);
        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.delete(nonExistingOrderItem));
    }

    @Test
    void deleteById_shouldRemoveOrderItem_whenOrderItemExists() {
        OrderItem newOrderItem = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        orderItemService.deleteById(newOrderItem.getId());
        assertFalse(orderItemService.findById(newOrderItem.getId()).isPresent());
    }

    @Test
    void deleteById_shouldThrowException_whenOrderItemDoesNotExist() {
        assertThrows(OrderItemNotFoundException.class, () -> orderItemService.deleteById(999L)); // Assuming 999L is a non-existing ID
    }

    @Test
    void findAll_shouldReturnAllOrderItems() {
        OrderItem orderItem1 = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        OrderItem orderItem2 = orderItemService.save(3, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(150.00), order.getId(), status.getId());
        List<OrderItem> allOrderItems = orderItemService.findAll();
        assertTrue(allOrderItems.contains(orderItem1));
        assertTrue(allOrderItems.contains(orderItem2));
        assertEquals(2, allOrderItems.size());
    }

    @Test
    void findAll_shouldReturnEmpty_whenNoOrderItemsExist() {
        List<OrderItem> allOrderItems = orderItemService.findAll();
        assertTrue(allOrderItems.isEmpty());
    }

    @Test
    void findByOrderId_shouldReturnOrderItemsForGivenOrder() {
        OrderItem orderItem1 = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        OrderItem orderItem2 = orderItemService.save(3, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(150.00), order.getId(), status.getId());
        List<OrderItem> orderItems = orderItemService.findByOrderId(order.getId());
        assertTrue(orderItems.contains(orderItem1));
        assertTrue(orderItems.contains(orderItem2));
        assertEquals(2, orderItems.size());
    }

    @Test
    void findByOrderId_shouldReturnEmpty_whenNoOrderItemsForGivenOrder() {
        List<OrderItem> orderItems = orderItemService.findByOrderId(999L); // Assuming 999L is a non-existing order ID
        assertTrue(orderItems.isEmpty());
    }

    @Test
    void findBySellerId_shouldReturnOrderItemsForGivenSeller() {
        OrderItem orderItem1 = orderItemService.save(2, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(100.00), order.getId(), status.getId());
        OrderItem orderItem2 = orderItemService.save(3, customer.getId(), seller.getId(), product.getId(), BigDecimal.valueOf(150.00), order.getId(), status.getId());
        List<OrderItem> orderItems = orderItemService.findBySellerId(seller.getId());
        assertTrue(orderItems.contains(orderItem1));
        assertTrue(orderItems.contains(orderItem2));
        assertEquals(2, orderItems.size());
    }

    @Test
    void findBySellerId_shouldReturnEmpty_whenNoOrderItemsForGivenSeller() {
        List<OrderItem> orderItems = orderItemService.findBySellerId(999L); // Assuming 999L is a non-existing seller ID
        assertTrue(orderItems.isEmpty());
    }
}
