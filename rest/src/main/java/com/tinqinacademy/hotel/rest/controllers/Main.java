package com.tinqinacademy.hotel.rest.controllers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tinqinacademy.hotel")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
