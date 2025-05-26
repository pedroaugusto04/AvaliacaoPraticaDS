package com.avaliacaods.bank.services.authentication;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.avaliacaods.bank.models.authentication.UserDetailsImpl;

@Service
public class JwtTokenService {

    @Value("${SECRET_KEY_JWT}")
    private String SECRET_KEY;

    @Value("${ISSUER_JWT}")
    private String ISSUER;

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER) 
                    .withIssuedAt(creationDate()) 
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername()) // nome de usuario como assunto do token ( no caso, o CPF)
                    .sign(algorithm); 
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) 
                    .build()
                    .verify(token) 
                    .getSubject(); // obtem o username associado ao token ( no caso, o CPF)
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token not valid or expired");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}