package com.example.transactionprocessing.config;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

@LoadBalancerClient(value = "ACCOUNT-MANAGEMENT-SERVICE")
public class AccountManagerLoadBalancer {

    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }
}
