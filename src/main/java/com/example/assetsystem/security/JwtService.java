package com.example.assetsystem.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey key;
    private final long accessExpireSeconds;
    private final long refreshExpireSeconds;

    public JwtService(@Value("${security.jwt.secret}") String secret,
                      @Value("${security.jwt.access-expire-seconds}") long accessExpireSeconds,
                      @Value("${security.jwt.refresh-expire-seconds}") long refreshExpireSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpireSeconds = accessExpireSeconds;
        this.refreshExpireSeconds = refreshExpireSeconds;
    }

    public String createAccessToken(Long userId, String username) {
        return token(userId, username, accessExpireSeconds, "access");
    }

    public String createRefreshToken(Long userId, String username) {
        return token(userId, username, refreshExpireSeconds, "refresh");
    }

    public Map<String, Object> parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private String token(Long userId, String username, long expireSeconds, String type) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username", username)
                .claim("type", type)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expireSeconds)))
                .signWith(key)
                .compact();
    }
}
