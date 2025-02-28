package com.tulipan.ordersapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrdersappApplicationTests {

    @BeforeAll
    public static void loadEnv() {
        // Intentar obtener las variables de entorno
        String rootPassword = System.getenv("MYSQL_ROOT_PASSWORD");
        String database = System.getenv("MYSQL_DATABASE");

        // Si las variables de entorno no están definidas, cargar desde .env
        if (rootPassword == null || database == null) {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            System.setProperty("MYSQL_ROOT_PASSWORD", dotenv.get("MYSQL_ROOT_PASSWORD"));
            System.setProperty("MYSQL_DATABASE", dotenv.get("MYSQL_DATABASE"));
        }
    }

    @Test
    void contextLoads() {
        // Si el contexto se carga correctamente, la prueba pasará.
        // No es necesario hacer ninguna aserción explícita, ya que si el contexto
        // no se carga, la prueba fallará automáticamente.
    }

}
