package com.psychology.demo.service;

import com.psychology.demo.Role;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

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
}
