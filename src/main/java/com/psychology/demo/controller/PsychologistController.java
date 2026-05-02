package com.psychology.demo.controller;

import com.psychology.demo.dto.PsychologistProfileRequest;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.service.PsychologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/psychologist")
@RequiredArgsConstructor
public class PsychologistController {
    private final PsychologistService psychologistService;

    @PostMapping("/profile")
    public ResponseEntity<String> saveProfile(@RequestBody PsychologistProfileRequest request, Principal principal) {
        return ResponseEntity.ok(psychologistService.updateOrCreateProfile(request, principal.getName()));
    }

    @GetMapping("/profile")
    public ResponseEntity<Psychologist> getProfile(Principal principal) {
        return ResponseEntity.ok(psychologistService.getMyProfile(principal.getName()));
    }
}