package com.psychology.demo.dto;

import lombok.Data;

@Data
public class PsychologistResponseDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private Integer experienceYears;
    private String language;
    private Double rating;
    private String imagePath;
}