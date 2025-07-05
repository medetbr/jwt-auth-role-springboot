package com.mdt.jwt;

import com.mdt.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // 1. Gizli anahtar
    private static final String SECRET_KEY = "5dc131672603d6f58543b2aef08875533d8eb31d3b4874058b1a6368d9874c5f";

    // 2. Token geçerlilik süresi (ms cinsinden)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 gün

    // 3. Anahtar objesi
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 4. Token üret
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) // payload: kimin için
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date(System.currentTimeMillis())) // ne zaman üretildi
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // ne zamana kadar geçerli
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // hangi algoritma ile imzala
                .compact();
    }
    // 5. Kullanıcı adını tokendan oku
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    // 6. token geçerli mi
    public boolean isTokenValid(String token, String username){
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 7. token süresi doldu mu
    public boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
