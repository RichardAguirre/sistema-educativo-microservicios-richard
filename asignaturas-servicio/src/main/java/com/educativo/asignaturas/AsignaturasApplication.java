package com.educativo.asignaturas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AsignaturasApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsignaturasApplication.class, args);
    }
}