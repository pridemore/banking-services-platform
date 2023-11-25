package com.example.transactionprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TransactionProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionProcessingApplication.class, args);
    }

}
