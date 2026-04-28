package com.example.autolog.infrastructure.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.autolog.domain.service.TokenService;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService implements TokenService {

    private static final String ISSUER = "auth-autolog-api";
    private static final String CLAIM_PURPOSE = "purpose";
    private static final String PURPOSE_PASSWORD_RESET = "password-reset";

    private final String secret;
    private final Long accessTokenExpirationHours;
    private final Long resetTokenExpirationMinutes;

    public JwtTokenService(
            @Value("${api.security.token.secret}") String secret,
            @Value("${api.security.token.access-expiration-hours:4}") Long accessTokenExpirationHours,
            @Value("${api.security.token.reset-expiration-minutes:15}") Long resetTokenExpirationMinutes
    ) {
        this.secret = secret;
        this.accessTokenExpirationHours = accessTokenExpirationHours;
        this.resetTokenExpirationMinutes = resetTokenExpirationMinutes;
    }

    @Override
    public String generateAccessToken(UserEntity user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getId())
                    .withClaim("workshopId", user.getWorkshop().getId())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate())
                    .sign(getAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating access token", exception);
        }
    }

    @Override
    public String validateAccessToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    @Override
    public String generatePasswordResetToken(UserEntity user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getId())
                    .withClaim("purpose", PURPOSE_PASSWORD_RESET)
                    .withExpiresAt(Instant.now().plus(resetTokenExpirationMinutes, ChronoUnit.MINUTES))
                    .sign(getAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating password reset token", exception);
        }
    }

    @Override
    public boolean validatePasswordResetToken(String token) {
        try {
            JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .withClaim(CLAIM_PURPOSE, PURPOSE_PASSWORD_RESET)
                    .build()
                    .verify(token);

            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    @Override
    public String extractEmailFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error extracting email from token", exception);
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.ofHours(-3));
    }
}