package com.psychology.demo.controller;

import com.psychology.demo.dto.AuthenticationRequest;
import com.psychology.demo.dto.RegisterRequest;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String name = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("ugurla registr olundunuz " + name + " bey!");
    }

    @PostMapping("/login")
    public ResponseEntity<Map> login(@Valid @RequestBody AuthenticationRequest request) {

        Authentication authentication = authService.login(request);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generetToken(principal);
        return ResponseEntity.ok(Map.of("message", "tokeni hec kimlese paylasmayin ve itirmeyin",
                    "token", token));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal MyUserDetails userDetails) {
        String username = userDetails.getUsername();
        List<String> uniqueRoles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
                "username", username,
                "roles", uniqueRoles

        ));
    }
}
