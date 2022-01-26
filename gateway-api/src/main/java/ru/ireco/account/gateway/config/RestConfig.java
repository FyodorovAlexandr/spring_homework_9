package ru.ireco.account.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
class RestConfig {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    WebClient webClient() {
        HttpClient httpClient = HttpClient
                .create()
                .wiretap(true);

        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}