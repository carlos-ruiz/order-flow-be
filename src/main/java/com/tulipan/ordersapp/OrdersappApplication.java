package com.tulipan.ordersapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OrdersappApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersappApplication.class, args);
    }

}
