package com.example.autolog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.autolog.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Rene
 */

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel userModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-autolog-api")
                    .withSubject(userModel.getEmail())
                    .withClaim("id", userModel.getIdUser())
                    .withClaim("cnpj", userModel.getCnpj())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);

        }

    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-autolog-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    public String passwordResetToken(UserModel userModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String resetToken = JWT.create()
                    .withIssuer("auth-autolog-api")
                    .withSubject(userModel.getEmail())
                    .withClaim("id", userModel.getIdUser())
                    .withClaim("purpose", "password-reset")
                    .withExpiresAt(LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.ofHours(-3)))
                    .sign(algorithm);
            return resetToken;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating reset token", exception);
        }
    }

    public boolean validateResetToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm)
                    .withIssuer("auth-autolog-api")
                    .withClaim("purpose", "password-reset")
                    .build();
            var decodedJWT = verifier.verify(token);

            return true;

        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String extractEmailFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm)
                    .withIssuer("auth-autolog-api")
                    .build();
            var decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw  new RuntimeException("Error extracting email token");
        }
    }



    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.ofHours(-3));
    }
}
