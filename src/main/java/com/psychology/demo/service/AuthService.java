package com.psychology.demo.service;

import com.psychology.demo.dto.TokenPair;
import com.psychology.demo.enumm.Role;
import com.psychology.demo.dto.AuthenticationRequest;
import com.psychology.demo.dto.RegisterRequest;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.UserRepository;
import com.psychology.demo.security.JWTService;
import com.psychology.demo.security.MyAuthenticationProvider;
import com.psychology.demo.security.MyUserDetailsService;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyAuthenticationProvider authenticationProvider;
    private final JWTService jwtService;


    public String register(RegisterRequest request) {
       if( userRepository.existsByEmail(request.getEmail())){
           throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu email artıq qeydiyyatdan keçib");       }

        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return request.getFullName();
    }

    public Authentication login(AuthenticationRequest request) {
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken
                (request.getEmail(), request.getPassword()));

        return authenticate;
    }


}