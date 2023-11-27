package com.example.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class CustormPreFilter implements GlobalFilter {

    @Value("${api.authorization.key}")
    private String apiKey;

    public Mono<Void> filter(final ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Pre filter called here :-----------");
        ServerHttpRequest request = exchange.getRequest();
        log.info("Authorization = " + request.getHeaders().getFirst("Authorization"));

        if (!hasValidAuthorization(request)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String errorMessage = "{\"error\":\"Unauthorized\"}";
            byte[] errorMessageBytes = errorMessage.getBytes();
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(errorMessageBytes)));
        }

        return chain.filter(exchange).then(Mono.<Void>fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            log.info("Post filter = " + response.getStatusCode());
        }));
    }

    private boolean hasValidAuthorization(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String authorizationHeader = headers.getFirst("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                && request.getHeaders().getFirst("Authorization")
                .substring(7).equals(apiKey)) {
            return true;
        }
        return false;
    }

}
