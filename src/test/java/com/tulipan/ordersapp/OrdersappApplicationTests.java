package com.tulipan.ordersapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OrdersappApplication.class)
class OrdersappApplicationTests {

    @Test
    void contextLoads() {
        // Si el contexto se carga correctamente, la prueba pasará.
        // No es necesario hacer ninguna aserción explícita, ya que si el contexto
        // no se carga, la prueba fallará automáticamente.
    }

    @Test
    void jpaAuditingEnabled() {
        EnableJpaAuditing annotation = OrdersappApplication.class.getAnnotation(EnableJpaAuditing.class);
        assertNotNull(annotation);
    }

    @Test
    void mainMethodStartsApplicationSuccessfully() {
        assertDoesNotThrow(() -> OrdersappApplication.main(new String[]{}));
    }

}
