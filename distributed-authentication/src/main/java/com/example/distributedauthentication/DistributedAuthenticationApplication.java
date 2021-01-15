package com.example.distributedauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.example")
@EnableDiscoveryClient
@SpringBootApplication
public class DistributedAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedAuthenticationApplication.class, args);
    }

}
