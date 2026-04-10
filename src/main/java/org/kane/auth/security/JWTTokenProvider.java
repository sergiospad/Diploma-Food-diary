package org.kane.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JWTTokenProvider {

    private final SecretKey key;

    public JWTTokenProvider(Environment env) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(env.getProperty("app.security.secret")));
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        Instant expirationDate = Instant.now().plusMillis(SecurityConstants.EXPIRATION_TIME);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expirationDate))
                .signWith(key)
                .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserId(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiry(token).before(new Date());
    }

    public Date extractExpiry(String bearerToken) {
        return extractClaimBody(bearerToken, Claims::getExpiration);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token).getPayload();
        return claims.get("id", Long.class);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token).getPayload();
        return claims.get("sub", String.class);
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    public String extractUserId(String token){
        return extractClaimBody(token, Claims::getSubject);
    }

    private <T> T extractClaimBody(String token, Function<Claims, T> claimsResolver) {
        Jws<Claims> claimsJws = extractClaims(token);
        return claimsResolver.apply(claimsJws.getPayload());
    }

    private Jws<Claims> extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}
