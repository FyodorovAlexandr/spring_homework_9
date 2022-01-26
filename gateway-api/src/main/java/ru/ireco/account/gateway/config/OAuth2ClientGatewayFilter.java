package ru.ireco.account.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.ireco.account.gateway.model.AuthRequest;
import ru.ireco.account.gateway.model.AuthResponse;

@Component
class OAuth2ClientGatewayFilter extends AbstractGatewayFilterFactory<String> {

    private static final String GRANT_TYPE = "client_credentials";

    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}")
    private String tokenUri;

    private final ReactiveClientRegistrationRepository clientRegistrationRepository;
    private final WebClient webClient;

    public OAuth2ClientGatewayFilter(ReactiveClientRegistrationRepository clientRegistrationRepository,
                                     WebClient webClient) {
        super(String.class);
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(String audience) {
        return (exchange, chain) -> exchange.getPrincipal()
                .filter(principal -> principal instanceof OAuth2AuthenticationToken)
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(authentication -> authorizedClient(authentication, audience))
                .map(token -> withBearerAuth(exchange, token))
                .defaultIfEmpty(exchange).flatMap(chain::filter);
    }

    private Mono<String> authorizedClient(OAuth2AuthenticationToken oauth2Authentication, String audience) {
        String clientRegistrationId = oauth2Authentication.getAuthorizedClientRegistrationId();
        Mono<AuthRequest> requestMono = clientRegistrationRepository
                .findByRegistrationId(clientRegistrationId)
                .flatMap(cr -> authRequest(cr, audience));
        return webClient.post()
                .uri(tokenUri + "oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestMono, AuthRequest.class)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(AuthResponse::getAccessToken);
    }

    private Mono<AuthRequest> authRequest(ClientRegistration clientRegistration, String audience) {
        return Mono.just(AuthRequest.builder()
                .clientId(clientRegistration.getClientId())
                .clientSecret(clientRegistration.getClientSecret())
                .audience(audience)
                .grantType(GRANT_TYPE)
                .build());
    }

    private ServerWebExchange withBearerAuth(ServerWebExchange exchange, String accessToken) {
        return exchange.mutate()
                .request(r -> r.headers(headers -> headers.setBearerAuth(accessToken)))
                .build();
    }

}

