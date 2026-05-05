package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    public RefreshToken(String token, String userName, LocalDateTime expiryDate) {
        this.token = token;
        this.userName = userName;
        this.expiryDate = expiryDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 500)
    private String token;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private LocalDateTime createDate=LocalDateTime.now();

    @Column(nullable = false)
    private boolean isUsed=false;


    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}
