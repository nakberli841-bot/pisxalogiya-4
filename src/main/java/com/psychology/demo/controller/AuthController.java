package com.psychology.demo.controller;

import com.psychology.demo.dto.*;
import com.psychology.demo.security.JwtService;
import com.psychology.demo.security.MyUserDetails;
import com.psychology.demo.security.MyUserDetailsService;
import com.psychology.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String name = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("ugurla registr olundunuz " + name + " bey!");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenPair> login(@Valid @RequestBody AuthenticationRequest request) {
        TokenPair tokenPair = authService.login(request);
        return ResponseEntity.ok(tokenPair);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal MyUserDetails userDetails) {
        UserDTO profile = authService.profile(userDetails);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.logout(userDetails.getUsername()));
    }
}
