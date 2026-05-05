package com.psychology.demo.security;

import com.psychology.demo.dto.TokenPair;
import com.psychology.demo.entity.RefreshToken;
import com.psychology.demo.repo.RefreshTokenRepository;
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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Service
public class JWTService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final String SECRET_KEY;

    public JWTService(RefreshTokenRepository refreshTokenRepository, @Value("${SECRET_KEY}")
    String secretKey) {
        this.refreshTokenRepository = refreshTokenRepository;
        SECRET_KEY = secretKey;
    }


    public String generetToken(UserDetails userDetails) {

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.stream().map(value -> value.getAuthority()).findFirst().orElseGet(() -> null);


        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSingnInKey(SECRET_KEY))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 15))
                .claim("role", role)
                .claim("type", "access")
                .compact();
    }


    public RefreshToken generateRefreshToken(String username) {
        // userin evvelki refresh tokenlerini deaktiv et
        refreshTokenRepository.markAllAsUsedByUserName(username);
        // Yeni refresh token yarat
        String refreshTokenValue = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);

       RefreshToken refreshToken=  new RefreshToken(username, refreshTokenValue, expiryDate);
      return refreshTokenRepository.save(refreshToken);

    }

    // login vaxti deyilde sonrada refresh token ile yeni acces token yaratmaq
    public String refreshAccessToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue).orElseThrow(() -> new RuntimeException("Refresh token bulunamadı"));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token vaxti bitib");
        }

        if (refreshToken.isUsed()) {
            throw new RuntimeException("Refresh token zaten istifade olunub");
        }

        // eks halda tokenin istifadesini true et
        refreshToken.setUsed(true);

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getUserName();
    }


    public TokenPair generateTokenPair(UserDetails userDetails) {
        String accessToken = generetToken(userDetails);
        RefreshToken refreshToken = generateRefreshToken(userDetails.getUsername());
        return new TokenPair(accessToken, refreshToken.getToken());
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

    // Refresh token doğrulanmasi
    public boolean validateRefreshToken(String refreshTokenValue) {
        return refreshTokenRepository.findByToken(refreshTokenValue)
                .map(token -> !token.isExpired() && !token.isUsed())
                .orElse(false);
    }

    // istifadecinin butüm refresh tokenlarını sil (Logout)
    public void revokeAllRefreshTokens(String username) {
        refreshTokenRepository.deleteByUserName(username);
    }

    // vaxti bitmis tokenları sil (Scheduled task için)
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }


}

