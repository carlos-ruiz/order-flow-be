package com.tulipan.ordersapp.products.infrastructure.converters;

import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.infrastructure.entities.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConverter {

    public static ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setFinalPrice(product.getFinalPrice());
        productEntity.setBasePrice(product.getBasePrice());
        productEntity.setColor(product.getColor());
        productEntity.setSize(product.getSize());
        return productEntity;
    }

    public static Product toModel(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setFinalPrice(productEntity.getFinalPrice());
        product.setBasePrice(productEntity.getBasePrice());
        product.setColor(productEntity.getColor());
        product.setSize(productEntity.getSize());
        return product;
    }
}
