package com.tulipan.ordersapp.orderitems.application;

import com.tulipan.ordersapp.customers.application.CustomerService;
import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.customers.domain.model.Customer;
import com.tulipan.ordersapp.orderitems.domain.model.OrderItem;
import com.tulipan.ordersapp.orderitems.domain.repository.OrderItemRepository;
import com.tulipan.ordersapp.orders.application.OrderService;
import com.tulipan.ordersapp.orders.domain.exceptions.OrderNotFoundException;
import com.tulipan.ordersapp.orders.domain.model.Order;
import com.tulipan.ordersapp.products.application.ProductService;
import com.tulipan.ordersapp.products.domain.ProductNotFoundException;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.sellers.application.SellerService;
import com.tulipan.ordersapp.sellers.domain.exceptions.SellerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.status.application.StatusService;
import com.tulipan.ordersapp.status.domain.exceptions.StatusNotFoundException;
import com.tulipan.ordersapp.status.domain.model.Status;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository repository;
    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final SellerService sellerService;
    private final StatusService statusService;

    public OrderItemServiceImpl(OrderItemRepository repository, OrderService orderService, ProductService productService, CustomerService customerService, SellerService sellerService, StatusService statusService) {
        this.repository = repository;
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.sellerService = sellerService;
        this.statusService = statusService;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return repository.update(orderItem);
    }

    @Override
    public void delete(OrderItem orderItem) {
        repository.delete(orderItem);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> findBySellerId(Long sellerId) {
        return repository.findBySellerId(sellerId);
    }

    @Override
    public OrderItem save(Integer quantity, Long customerId, Long sellerId, Long productId, BigDecimal price, Long orderId, Long statusId) {
        Order order = orderService.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        Product product = productService.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Customer customer = customerService.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Seller seller = sellerService.findById(sellerId).orElseThrow(() -> new SellerNotFoundException(sellerId));
        Status status = null;

        if (statusId == null) {
            status = statusService.findByName("Pending").orElse(new Status(null, "Pending", true));
            status = statusService.save(status);
        } else {
            status = statusService.findById(statusId).orElseThrow(() -> new StatusNotFoundException(statusId));
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        OrderItem orderItem = OrderItem.builder()
            .quantity(quantity)
            .price(price)
            .order(order)
            .product(product)
            .customer(customer)
            .seller(seller)
            .status(status)
            .build();
        return repository.save(orderItem);
    }
}
