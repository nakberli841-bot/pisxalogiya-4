package com.psychology.demo.controller;

import com.psychology.demo.dto.*;
import com.psychology.demo.security.JWTService;
import com.psychology.demo.security.MyUserDetails;
import com.psychology.demo.security.MyUserDetailsService;
import com.psychology.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JWTService jwtService;
    private final MyUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String name = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("ugurla registr olundunuz " + name + " bey!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthenticationRequest request) {

        Authentication authentication = authService.login(request);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        TokenPair tokenPair = jwtService.generateTokenPair(principal);

        return ResponseEntity.ok("accessToken="+tokenPair.getAccessToken()+" refreshToken="+tokenPair.getRefreshToken());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal MyUserDetails userDetails) {
        String username = userDetails.getUsername();
        List<String> uniqueRoles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .distinct()
                .collect(Collectors.toList());
        UserDTO userDTO=new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(uniqueRoles);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            String username = jwtService.refreshAccessToken(request.getRefreshToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Yeni token ctunu yarat (Token Rotation)
            TokenPair newTokenPair = jwtService.generateTokenPair(userDetails);

            return ResponseEntity.ok(newTokenPair);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        try {
            String username = jwtService.getUserNameFromToken(request.getAccessToken());
            jwtService.revokeAllRefreshTokens(username);

            return ResponseEntity.ok("Logout ugurlu");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Logout ugursuz");
        }
    }
}
