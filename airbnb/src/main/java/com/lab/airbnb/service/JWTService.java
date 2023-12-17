package com.lab.airbnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lab.airbnb.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expireInMinutes}")
    private int expireInMinutes;

    private Algorithm algorithm;
    private static final String CLAIM_KEY = "userId";
    private static final String EMAIL_KEY = "email";

    private static final String RESET_PASSWORD_KEY = "resetPassword";

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public boolean isExpired(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build().verify(token);
        return decodedJWT.getExpiresAt().before(new Date());

    }
    public String generateToken(String userId) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(CLAIM_KEY, userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + (long) expireInMinutes * 60 * 1000))
                .sign(algorithm);
    }

    public String generateVerificationToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (long) expireInMinutes * 60 * 1000))
                .sign(algorithm);
    }

    public String generatePasswordResetToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(RESET_PASSWORD_KEY, user.getEmail())
                //should be 30 minutes
                .withExpiresAt(new Date(System.currentTimeMillis() + (long) expireInMinutes * 60 * 1000))
                .sign(algorithm);
    }

    public String getResetPasswordEmail(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return decodedJWT.getClaim(RESET_PASSWORD_KEY)
                .asString();
    }

    public String getUserId(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return decodedJWT.getClaim(CLAIM_KEY)
                .asString();
    }
}
