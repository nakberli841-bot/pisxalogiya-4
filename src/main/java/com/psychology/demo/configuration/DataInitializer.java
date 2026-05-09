package com.psychology.demo.configuration;

import com.psychology.demo.enums.Role;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.setup.admin.email}")
    private String adminEmail;

    @Value("${app.setup.admin.password}")
    private String adminPassword;

    @Value("${app.setup.admin.full-name}")
    private String adminFullName;


    @Override
    public void run(String... args) throws Exception {
      Optional<User> user= userRepository.findByEmail(adminEmail);
     if(user.isEmpty() ) {
         User userEntity = User.builder().email(adminEmail)
                         .role(Role.ADMIN).fullName(adminFullName)
                         .password(passwordEncoder.encode(adminPassword))
                                 .build();
         userRepository.save(userEntity);
         log.info("Sistem admini yaradıldı: {}", adminEmail);
     }else {
         log.info("Admin artıq mövcuddur.");
     }

    }
}
