package com.example.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class CustormPreFilter implements GlobalFilter {

    public Mono<Void> filter(final ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Pre filter called here :-----------");
        ServerHttpRequest request = exchange.getRequest();
        log.info("Authorization = "+ request.getHeaders().getFirst("Authorization"));

        //rerouting to whatever the correct service
        //return chain.filter(exchange);

        //post filter and prefilter re-routing;
        return  chain.filter(exchange).then(Mono.<Void>fromRunnable(()->{
            ServerHttpResponse response=exchange.getResponse();
            log.info("Post filter = "+response.getStatusCode());
        }));
    }
}
