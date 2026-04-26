package com.psychology.demo.controller;

import com.psychology.demo.dto.PsychologistDetailDTO;
import com.psychology.demo.dto.PsychologistResponseDTO;
import com.psychology.demo.service.PsychologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/psychologists")
@RequiredArgsConstructor
public class PsychologistController {
    private final PsychologistService psychologistService;

    @GetMapping
    public ResponseEntity<List<PsychologistResponseDTO>> getAll() {
        return ResponseEntity.ok(psychologistService.getAllPsychologists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsychologistDetailDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(psychologistService.getPsychologistById(id));
    }
}