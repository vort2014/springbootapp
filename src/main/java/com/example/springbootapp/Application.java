package com.example.springbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
