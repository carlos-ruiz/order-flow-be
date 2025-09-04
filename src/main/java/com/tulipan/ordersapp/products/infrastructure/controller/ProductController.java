package com.tulipan.ordersapp.products.infrastructure.controller;

import com.tulipan.ordersapp.products.application.ProductService;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.infrastructure.dto.ProductRequestDTO;
import com.tulipan.ordersapp.products.infrastructure.dto.ProductResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductResponseDTO> productDTOs = products.stream()
            .map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .basePrice(product.getBasePrice())
                .color(product.getColor())
                .finalPrice(product.getFinalPrice())
                .build())
            .toList();
        return ResponseEntity.ok(productDTOs);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product savedProduct = productService.save(Product.builder()
            .name(productRequestDTO.getName())
            .description(productRequestDTO.getDescription())
            .basePrice(productRequestDTO.getBasePrice())
            .color(productRequestDTO.getColor())
            .size(productRequestDTO.getSize())
            .finalPrice(productRequestDTO.getFinalPrice())
            .build());
        ProductResponseDTO responseDTO = ProductResponseDTO.builder()
            .id(savedProduct.getId())
            .name(savedProduct.getName())
            .description(savedProduct.getDescription())
            .basePrice(savedProduct.getBasePrice())
            .color(savedProduct.getColor())
            .size(savedProduct.getSize())
            .finalPrice(savedProduct.getFinalPrice())
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
