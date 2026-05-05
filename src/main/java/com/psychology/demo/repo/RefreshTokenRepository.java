package com.psychology.demo.repo;

import com.psychology.demo.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserNameAndisUsedFalse(String Username);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);

    @Modifying
    @Transactional
    void deleteByUserName(String username);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.isUsed = true WHERE rt.userName = :username")
    void markAllAsUsedByUserName(String username);
}
