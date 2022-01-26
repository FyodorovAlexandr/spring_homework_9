package ru.iteco.account.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
class JwtProviderImpl implements JwtProvider {

    @Value("${security.jwt.expiration}")
    private int expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public String generateJwt(String username) {
        Date expirationDate = Date.from(Instant.now().plusSeconds(expiration));
        return Jwts.builder()
                .claim(Claims.SUBJECT, username)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public boolean validateJwt(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT", e);
        }
        return false;
    }

    @Override
    public String getUserNameFromJwt(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }
}
