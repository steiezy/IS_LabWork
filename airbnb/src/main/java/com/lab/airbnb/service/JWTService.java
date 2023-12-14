package com.lab.airbnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(algorithmKey);
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
                .withClaim(EMAIL_KEY,user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (long) expireInMinutes * 60 * 1000))
                .sign(algorithm);
    }

    public String getUserId(String token) {
        return JWT.decode(token)
                .getClaim(CLAIM_KEY)
                .asString();
    }
}
