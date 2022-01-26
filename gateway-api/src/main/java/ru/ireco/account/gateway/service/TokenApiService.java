package ru.ireco.account.gateway.service;

public interface TokenApiService {

    String getToken(String clientId, String clientSecret, String audience);

}
