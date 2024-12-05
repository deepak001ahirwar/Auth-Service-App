package com.deepak.auth.app.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    public static String generateToken(Map<String, Object> claims, String user, long expiresIn, String privateKey, List<String> roles)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        claims.put("roles", roles);
        return generateToken(user, expiresIn, claims, privateKey);
    }

    private static String generateToken(String subject, long expiresIn, Map<String, Object> claims, String key)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expiresIn, ChronoUnit.SECONDS)))
                .signWith(KeyUtil.readPrivateKey(key))
                .compact();

        return jwtToken;
    }
}
