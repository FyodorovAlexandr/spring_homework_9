package ru.iteco.account.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.iteco.account.model.UserAuthDto;
import ru.iteco.account.security.jwt.JwtProvider;
import ru.iteco.account.service.UserAuthService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthService userAuthService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserAuthService userAuthService, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userAuthService = userAuthService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public String createUserAuth(@RequestBody UserAuthDto userAuthDto) {
        return userAuthService.create(userAuthDto);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String logpass) {
        String[] logpassArray = new String(
                Base64.getDecoder().decode(logpass.substring(6)), StandardCharsets.UTF_8
        ).split(":");

        String login = logpassArray[0];
        String password = logpassArray[1];

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String jwtToken = jwtProvider.generateJwt(login);
        return ResponseEntity
                .ok()
                .header("access_token", jwtToken)
                .build();
    }

}
