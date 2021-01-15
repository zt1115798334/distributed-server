package com.example.distributedbookdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DistributedBookDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedBookDetailsApplication.class, args);
    }

}
