package com.cyecize.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConnectionPerSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectionPerSessionApplication.class, args);
    }

}
