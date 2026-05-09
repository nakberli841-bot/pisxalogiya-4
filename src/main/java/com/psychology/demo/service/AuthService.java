package com.psychology.demo.service;

import com.psychology.demo.dto.*;
import com.psychology.demo.enums.Role;
import com.psychology.demo.entity.User;
import com.psychology.demo.excception.BusinessLogicException;
import com.psychology.demo.repo.UserRepository;
import com.psychology.demo.security.JwtService;
import com.psychology.demo.security.MyAuthenticationProvider;
import com.psychology.demo.security.MyUserDetails;
import com.psychology.demo.security.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyAuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;


    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessLogicException("bu email artiq qeydiyyatdan kecib");
        }

        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return request.getFullName();
    }

    public TokenPair login(AuthenticationRequest request) {
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken
                (request.getEmail(), request.getPassword()));
        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        TokenPair tokenPair = jwtService.generateTokenPair(principal);
        return tokenPair;
    }

    public UserDTO profile(MyUserDetails userDetails) {
        String username = userDetails.getUsername();
        List<String> uniqueRoles = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .distinct()
                .collect(Collectors.toList());
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(uniqueRoles);
        return userDTO;
    }

    public TokenPair refreshToken(RefreshTokenRequest request) {
        String username = jwtService.refreshAccessToken(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        TokenPair newTokenPair = jwtService.generateTokenPair(userDetails);
        return newTokenPair;
    }


    public String logout(String  username){
        if (username == null) {
            throw new UsernameNotFoundException("logout olunmaq isteyen istifadeci tapilmadi");
        }
        jwtService.revokeAllRefreshTokens(username);
        return "logout ugurlu";
    }


}