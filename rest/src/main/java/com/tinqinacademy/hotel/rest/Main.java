package com.tinqinacademy.hotel.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.tinqinacademy.hotel")
@EntityScan(basePackages = "com.tinqinacademy.hotel.persistance.entities")
@EnableJpaRepositories(basePackages = "com.tinqinacademy.hotel.persistance.repositories")
@EnableFeignClients(basePackages = "com.tinqinacademy.hotel.restexport")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
