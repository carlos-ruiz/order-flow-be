package com.tulipan.ordersapp.products.infrastructure.controller;

import com.tulipan.ordersapp.products.application.ProductService;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.infrastructure.dto.ProductRequestDTO;
import com.tulipan.ordersapp.products.infrastructure.dto.ProductResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    private final ProductService productService = mock(ProductService.class);
    private final ProductController productController = new ProductController(productService);

    @Test
    @DisplayName("getAllProducts returns all products with correct fields")
    void getAllProductsReturnsAllProductsWithCorrectFields() {
        Product product = Product.builder()
            .id(1L)
            .name("Product One")
            .description("Description One")
            .basePrice(BigDecimal.valueOf(100))
            .color("Green")
            .finalPrice(BigDecimal.valueOf(120))
            .build();
        when(productService.findAll()).thenReturn(List.of(product));
        ResponseEntity<List<ProductResponseDTO>> response = productController.getAllProducts();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);

        ProductResponseDTO responseDTO = response.getBody().get(0);
        assertThat(responseDTO.getId()).isEqualTo(1L);
        assertThat(responseDTO.getName()).isEqualTo("Product One");
        assertThat(responseDTO.getDescription()).isEqualTo("Description One");
        assertThat(responseDTO.getBasePrice()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(responseDTO.getColor()).isEqualTo("Green");
        assertThat(responseDTO.getFinalPrice()).isEqualTo(BigDecimal.valueOf(120));
    }

    @Test
    @DisplayName("createProduct returns CREATED status with product data")
    void createProductReturnsCreatedStatusWithProductData() {
        // Setup request DTO
        ProductRequestDTO requestDTO = ProductRequestDTO.builder()
            .name("Test Product")
            .description("Test Description")
            .basePrice(BigDecimal.valueOf(99.99))
            .color("Red")
            .size("L")
            .finalPrice(BigDecimal.valueOf(119.99))
            .build();

        // Setup mock service response
        Product savedProduct = Product.builder()
            .id(42L)
            .name("Test Product")
            .description("Test Description")
            .basePrice(BigDecimal.valueOf(99.99))
            .color("Red")
            .size("L")
            .finalPrice(BigDecimal.valueOf(119.99))
            .build();
        when(productService.save(any(Product.class))).thenReturn(savedProduct);

        // Execute the endpoint
        ResponseEntity<ProductResponseDTO> response = productController.createProduct(requestDTO);

        // Verify response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(42L);
    }
}
