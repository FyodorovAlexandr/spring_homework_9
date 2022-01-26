package ru.iteco.account.currency.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplateExchange(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }


}
