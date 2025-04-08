package com.tulipan.ordersapp.products.application;

import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdReturnsProductWhenExists() {
        Long id = 1L;
        Product product = Product.builder()
            .name("Product1")
            .color("Red")
            .build();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void findByIdReturnsEmptyWhenNotExists() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void saveProductSuccessfully() {
        Product product = Product.builder()
            .id(1L)
            .name("Product1")
            .color("Red")
            .build();
        Product savedProduct = Product.builder()
            .id(1L)
            .name("Product1")
            .color("Red")
            .build();
        when(productRepository.save(product)).thenReturn(savedProduct);

        Product result = productService.save(product);

        assertNotNull(result.getId());
        assertEquals(savedProduct, result);
    }

    @Test
    void updateProductSuccessfully() {
        Product product = Product.builder()
            .name("Product1")
            .color("Red")
            .build();
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertEquals(product, result);
    }

    @Test
    void deleteByIdSuccessfully() {
        Long id = 1L;
        doNothing().when(productRepository).deleteById(id);

        productService.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProductSuccessfully() {
        Product product = Product.builder()
            .name("Product1")
            .color("Red")
            .build();
        doNothing().when(productRepository).delete(product);

        productService.delete(product);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void findAllReturnsListOfProducts() {
        Product product = Product.builder()
            .name("Product1")
            .color("Red")
            .build();
        Product product2 = Product.builder()
            .name("Product2")
            .color("Blue")
            .build();
        List<Product> products = List.of(
            product, product2
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(products.size(), result.size());
        assertEquals(products, result);
    }
}
