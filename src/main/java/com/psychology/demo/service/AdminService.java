package com.psychology.demo.service;

import com.psychology.demo.dto.RegisterRequest;
import com.psychology.demo.enumm.Role;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void transferOwnershipToDoctor(Long doctorId, Long adminId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Həkim tapılmadı"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin tapılmadı"));

        doctor.setRole(Role.ADMIN);

        admin.setRole(Role.USER);

        userRepository.save(doctor);
        userRepository.save(admin);
    }

    public Map<String,String> registrDocWithAdmin(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Bu email artıq qeydiyyatdan keçib");
        }

        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DOCTOR)
                .build();
        userRepository.save(user);

        return Map.of("email", request.getEmail(), "password", request.getPassword());
    }
}
