package com.tulipan.ordersapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class OrdersappApplication {

	public static void main(String[] args) {
		// Cargar el archivo .env
		Dotenv dotenv = Dotenv.configure().load();

		// Establecer las variables de entorno
		System.setProperty("MYSQL_ROOT_PASSWORD", dotenv.get("MYSQL_ROOT_PASSWORD"));
		System.setProperty("MYSQL_DATABASE", dotenv.get("MYSQL_DATABASE"));

		SpringApplication.run(OrdersappApplication.class, args);
	}

}
