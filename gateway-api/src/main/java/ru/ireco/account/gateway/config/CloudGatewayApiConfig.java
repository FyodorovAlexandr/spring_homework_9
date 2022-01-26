package ru.ireco.account.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CloudGatewayApiConfig {

    @Value("${service.stock.uri}")
    private String stockUri;
    @Value("${service.stock.audience}")
    private String stockAudience;
    @Value("${service.stock.path.stock-quotes}")
    private String stockQuotes;

    @Value("${service.currency.uri}")
    private String currencyUri;
    @Value("${service.currency.audience}")
    private String currencyAudience;
    @Value("${service.currency.path.all-exchange}")
    private String allExchangePath;

    private final OAuth2ClientGatewayFilter filterFactory;

    CloudGatewayApiConfig(OAuth2ClientGatewayFilter filterFactory) {
        this.filterFactory = filterFactory;
    }

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("get-stock-quotes", r -> r.path(stockQuotes)
                        .filters(f -> f.filter(filterFactory.apply(stockAudience)).removeRequestHeader("Cookie"))
                        .uri(stockUri))
                .route("all-exchange", r -> r.path(allExchangePath)
                        .filters(f -> f.filter(filterFactory.apply(currencyAudience)).removeRequestHeader("Cookie"))
                        .uri(currencyUri))
                .build();
    }

}