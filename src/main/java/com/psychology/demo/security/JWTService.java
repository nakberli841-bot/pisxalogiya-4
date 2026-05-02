package com.psychology.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;


@Service
public class JWTService {


    private final String SECRET_KEY;

    public JWTService(@Value("${SECRET_KEY}") String secretKey) {
        SECRET_KEY = secretKey;
    }


    public String generetToken(UserDetails userDetails) {

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.stream().map(value -> value.getAuthority()).findFirst().orElseGet(() -> null);


        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSingnInKey(SECRET_KEY))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 24))
                .claim("role", role)
                .compact();
    }

    public SecretKey getSingnInKey(String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        SecretKey secretKey1 = Keys.hmacShaKeyFor(decode);
        return secretKey1;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingnInKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public String getUserRoleFromToken(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSingnInKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String role = claims.get("role", String.class);
        return role;
    }

    public Date getExpirationDateFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingnInKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isExpiredToken(String token) {
        if (getExpirationDateFromToken(token).before(new Date())) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return userDetails.getUsername().equals(username) && !isExpiredToken(token);
    }
}
