package com.psychology.demo.service;

import com.psychology.demo.dto.PsychologistDetailDTO;
import com.psychology.demo.dto.PsychologistResponseDTO;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.repo.PsychologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PsychologistService {
    private final PsychologistRepository psychologistRepository;

    public List<PsychologistResponseDTO> getAllPsychologists() {
        return psychologistRepository.findAll().stream()
                .map(p -> {
                    PsychologistResponseDTO dto = new PsychologistResponseDTO();
                    dto.setId(p.getId());
                    dto.setFullName(p.getFirstName() + " " + p.getLastName());
                    dto.setSpecialty(p.getSpecialty());
                    dto.setExperienceYears(p.getExperienceYears());
                    dto.setImagePath(p.getImagePath());
                    return dto;
                }).collect(Collectors.toList());
    }

    public PsychologistDetailDTO getPsychologistById(Long id) {
        Psychologist p = psychologistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Psixoloq tapılmadı"));
        return new PsychologistDetailDTO();
    }
}