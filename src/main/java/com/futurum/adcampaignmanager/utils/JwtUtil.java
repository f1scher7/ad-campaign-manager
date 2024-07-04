package com.futurum.adcampaignmanager.utils;

import com.futurum.adcampaignmanager.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public final class JwtUtil {

    private static final String JWT_SECRET_KEY = "Lvr9Qmc9yYgjf/7XKKzoLLOT7ZzvItWpas8oUV1Re1o="; // wiem, że to ma być zmienna środowiskowa :)
    private static final long JWT_EXPIRATION_TIME = 864_000_000;

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Key key = getSigningKey();

        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + JWT_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static boolean validateToken(String token, User user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}

