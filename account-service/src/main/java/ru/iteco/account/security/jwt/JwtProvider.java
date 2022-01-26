package ru.iteco.account.security.jwt;

public interface JwtProvider {

    String generateJwt(String username);
    boolean validateJwt(String token);
    String getUserNameFromJwt(String token);

}
