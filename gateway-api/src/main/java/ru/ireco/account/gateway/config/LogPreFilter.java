package ru.ireco.account.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LogPreFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global Pre Filter executed");
        log.info("Path: {}", exchange.getRequest().getPath());
        log.info("Headers: {}", exchange.getRequest().getHeaders());
        return chain.filter(exchange);
    }

}
