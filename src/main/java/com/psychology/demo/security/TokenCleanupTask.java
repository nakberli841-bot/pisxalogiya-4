package com.psychology.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class TokenCleanupTask {

    @Autowired
    private JWTService jwtService;

    // Her gün gece 2 de vaxti kecmis tokenleri temizle
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredTokens() {
        jwtService.cleanupExpiredTokens();
        System.out.println("vaxti kecmis tokenler temizlendi: " + new Date());
    }
}