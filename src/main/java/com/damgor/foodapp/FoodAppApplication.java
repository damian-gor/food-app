package com.damgor.foodapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FoodAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodAppApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
