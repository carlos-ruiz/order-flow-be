package com.tulipan.ordersapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
class OrdersappApplicationTests {

	@BeforeAll
	public static void loadEnv() {
		// Cargar el archivo .env
		Dotenv dotenv = Dotenv.configure().load();

		// Establecer las variables de entorno
		System.setProperty("MYSQL_ROOT_PASSWORD", dotenv.get("MYSQL_ROOT_PASSWORD"));
		System.setProperty("MYSQL_DATABASE", dotenv.get("MYSQL_DATABASE"));
	}

	@Test
	void contextLoads() {
		// Si el contexto se carga correctamente, la prueba pasará.
		// No es necesario hacer ninguna aserción explícita, ya que si el contexto
		// no se carga, la prueba fallará automáticamente.
	}

}
