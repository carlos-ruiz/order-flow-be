package com.tulipan.ordersapp.sellers.infrastructure.repository;

import com.tulipan.ordersapp.customers.domain.exceptions.CustomerNotFoundException;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import com.tulipan.ordersapp.sellers.infrastructure.entities.SellerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SellerRepositoryAdapterTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerRepositoryAdapter sellerRepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_SellerSuccessfullySaved() {
        Seller seller = Seller.builder().id(1L).name("John").build();
        SellerEntity entity = SellerEntity.builder().id(1L).name("John").build();
        when(sellerRepository.save(any(SellerEntity.class))).thenReturn(entity);

        Seller savedSeller = sellerRepositoryAdapter.save(seller);

        assertNotNull(savedSeller);
        assertEquals(1L, savedSeller.getId());
        assertEquals("John", savedSeller.getName());
    }

    @Test
    void findById_SellerExists() {
        SellerEntity entity = SellerEntity.builder().id(1L).name("John").build();
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        Optional<Seller> foundSeller = sellerRepositoryAdapter.findById(1L);

        assertTrue(foundSeller.isPresent());
        assertEquals(1L, foundSeller.get().getId());
        assertEquals("John", foundSeller.get().getName());
    }

    @Test
    void findById_SellerDoesNotExist() {
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Seller> foundSeller = sellerRepositoryAdapter.findById(1L);

        assertFalse(foundSeller.isPresent());
    }

    @Test
    void findAll_ReturnsListOfSellers() {
        List<SellerEntity> entities = List.of(
            SellerEntity.builder().id(1L).name("John").build(),
            SellerEntity.builder().id(2L).name("Jane").build()
        );
        when(sellerRepository.findAll()).thenReturn(entities);

        List<Seller> sellers = sellerRepositoryAdapter.findAll();

        assertEquals(2, sellers.size());
        assertEquals(1L, sellers.get(0).getId());
        assertEquals("John", sellers.get(0).getName());
        assertEquals(2L, sellers.get(1).getId());
        assertEquals("Jane", sellers.get(1).getName());
    }

    @Test
    void deleteById_SellerSuccessfullyDeleted() {
        doNothing().when(sellerRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> sellerRepositoryAdapter.deleteById(1L));
        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_SellerSuccessfullyDeleted() {
        Seller seller = Seller.builder().id(1L).name("John").build();
        SellerEntity entity = SellerEntity.builder().id(1L).name("John").build();
        doNothing().when(sellerRepository).delete(any(SellerEntity.class));

        assertDoesNotThrow(() -> sellerRepositoryAdapter.delete(seller));
        verify(sellerRepository, times(1)).delete(entity);
    }

    @Test
    void update_SellerSuccessfullyUpdated() {
        Seller seller = Seller.builder().id(1L).name("John Updated").build();
        SellerEntity entity = SellerEntity.builder().id(1L).name("John Updated").build();
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(sellerRepository.save(any(SellerEntity.class))).thenReturn(entity);

        Seller updatedSeller = sellerRepositoryAdapter.update(seller);

        assertNotNull(updatedSeller);
        assertEquals(1L, updatedSeller.getId());
        assertEquals("John Updated", updatedSeller.getName());
    }

    @Test
    void update_SellerNotFound() {
        Seller seller = Seller.builder().id(1L).name("John Updated").build();
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> sellerRepositoryAdapter.update(seller));
    }
}
