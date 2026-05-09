package com.psychology.demo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupTask {

    private final JwtService jwtService;

    // Her gün gece 2 de vaxti kecmis tokenleri temizle
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredTokens() {
        jwtService.cleanupExpiredTokens();
       log.info("vaxti kecmis tokenler temizlendi: " + new Date());
    }
}