package com.tulipan.ordersapp.sellers.infrastructure.controller;

import com.tulipan.ordersapp.GlobalExceptionHandler;
import com.tulipan.ordersapp.sellers.application.SellerService;
import com.tulipan.ordersapp.sellers.domain.model.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SellerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void createSeller_ReturnsCreatedSeller() throws Exception {
        Seller seller = Seller.builder().id(1L).name("Seller1").build();
        when(sellerService.save(any(Seller.class))).thenReturn(seller);

        mockMvc.perform(post("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Seller1\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(seller.getId()))
            .andExpect(jsonPath("$.name").value(seller.getName()));
    }

    @Test
    void createSeller_DuplicateEmail_ReturnsConflict() throws Exception {
        when(sellerService.save(any())).thenThrow(new DataIntegrityViolationException("Duplicate entry"));
        mockMvc.perform(post("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Seller1\", \"phone\":\"1234567890\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    void getSellerById_SellerExists() throws Exception {
        Seller seller = Seller.builder().id(1L).name("Seller1").build();
        when(sellerService.findById(anyLong())).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/sellers/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(seller.getId()))
            .andExpect(jsonPath("$.name").value(seller.getName()));
    }

    @Test
    void getSellerById_SellerDoesNotExist() throws Exception {
        when(sellerService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/sellers/{id}", 1L))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllSellers_ReturnsListOfSellers() throws Exception {
        List<Seller> sellers = List.of(
            Seller.builder().id(1L).name("Seller1").build(),
            Seller.builder().id(2L).name("Seller2").build());
        when(sellerService.findAll()).thenReturn(sellers);

        mockMvc.perform(get("/sellers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Seller1"))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].name").value("Seller2"));
    }

    @Test
    void updateSeller_ReturnsUpdatedSeller() throws Exception {
        Seller seller = Seller.builder().id(1L).name("UpdatedSeller").build();
        when(sellerService.update(any(Seller.class))).thenReturn(seller);

        mockMvc.perform(put("/sellers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"UpdatedSeller\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(seller.getId()))
            .andExpect(jsonPath("$.name").value(seller.getName()));
    }

    @Test
    void deleteSeller_SellerExists() throws Exception {
        Seller seller = Seller.builder().id(1L).name("Seller1").build();
        when(sellerService.findById(anyLong())).thenReturn(Optional.of(seller));
        doNothing().when(sellerService).delete(any(Seller.class));

        mockMvc.perform(delete("/sellers/{id}", 1L))
            .andExpect(status().isNoContent());

        verify(sellerService, times(1)).delete(any(Seller.class));
    }

    @Test
    void deleteSeller_SellerDoesNotExist() throws Exception {
        when(sellerService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/sellers/{id}", 1L))
            .andExpect(status().isNotFound());
    }
}
