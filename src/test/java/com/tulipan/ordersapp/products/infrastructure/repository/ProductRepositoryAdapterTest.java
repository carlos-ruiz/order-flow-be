package com.tulipan.ordersapp.products.infrastructure.repository;

import com.tulipan.ordersapp.products.domain.ProductNotFoundException;
import com.tulipan.ordersapp.products.domain.model.Product;
import com.tulipan.ordersapp.products.infrastructure.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {

    @Mock
    private JpaProductRepository jpaProductRepository;

    @InjectMocks
    private ProductRepositoryAdapter productRepositoryAdapter;

    @Test
    void findByIdReturnsProductWhenFound() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        when(jpaProductRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Product> result = productRepositoryAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findByIdReturnsEmptyWhenNotFound() {
        when(jpaProductRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productRepositoryAdapter.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void savePersistsAndReturnsProduct() {
        Product product = new Product();
        product.setId(1L);
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        when(jpaProductRepository.save(any(ProductEntity.class))).thenReturn(entity);

        Product result = productRepositoryAdapter.save(product);

        assertEquals(1L, result.getId());
    }

    @Test
    void deleteByIdRemovesProduct() {
        doNothing().when(jpaProductRepository).deleteById(1L);

        assertDoesNotThrow(() -> productRepositoryAdapter.deleteById(1L));
        verify(jpaProductRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRemovesProduct() {
        Product product = new Product();
        product.setId(1L);
        doNothing().when(jpaProductRepository).delete(any(ProductEntity.class));

        assertDoesNotThrow(() -> productRepositoryAdapter.delete(product));
        verify(jpaProductRepository, times(1)).delete(any(ProductEntity.class));
    }

    @Test
    void findAllReturnsListOfProducts() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        when(jpaProductRepository.findAll()).thenReturn(List.of(entity));

        List<Product> result = productRepositoryAdapter.findAll();

        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void updateThrowsExceptionWhenProductNotFound() {
        Product product = new Product();
        product.setId(1L);
        when(jpaProductRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productRepositoryAdapter.update(product));
    }

    @Test
    void updateUpdatesAndReturnsProduct() {
        Product product = new Product();
        product.setId(1L);
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        when(jpaProductRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(jpaProductRepository.save(any(ProductEntity.class))).thenReturn(entity);

        Product result = productRepositoryAdapter.update(product);

        assertEquals(1L, result.getId());
    }
}
