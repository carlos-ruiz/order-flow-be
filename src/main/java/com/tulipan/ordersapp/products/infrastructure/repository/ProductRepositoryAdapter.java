package com.tulipan.ordersapp.products.infrastructure.repository;

import com.tulipan.ordersapp.products.domain.ProductNotFoundException;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.domain.repository.ProductRepository;
import com.tulipan.ordersapp.products.infrastructure.entities.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id).map(this::toModel);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);
        ProductEntity savedEntity = jpaProductRepository.save(entity);
        return toModel(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaProductRepository.deleteById(id);
    }

    @Override
    public void delete(Product product) {
        jpaProductRepository.delete(toEntity(product));
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
            .stream()
            .map(this::toModel)
            .toList();
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = jpaProductRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException(product.getId()));
        entity.setName(product.getName());
        entity.setBasePrice(product.getBasePrice());
        entity.setColor(product.getColor());
        entity.setSize(product.getSize());
        entity.setFinalPrice(product.getFinalPrice());
        ProductEntity updatedEntity = jpaProductRepository.save(entity);
        return toModel(updatedEntity);
    }

    public Product toModel(ProductEntity entity) {
        return Product.builder()
            .id(entity.getId())
            .name(entity.getName())
            .basePrice(entity.getBasePrice())
            .color(entity.getColor())
            .size(entity.getSize())
            .finalPrice(entity.getFinalPrice())
            .description(entity.getDescription())
            .build();
    }

    public ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
            .id(product.getId())
            .name(product.getName())
            .basePrice(product.getBasePrice())
            .color(product.getColor())
            .size(product.getSize())
            .finalPrice(product.getFinalPrice())
            .description(product.getDescription())
            .build();
    }
}
