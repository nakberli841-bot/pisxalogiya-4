package com.psychology.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PsychologistProfileRequest {
    private String specialty;
    private Integer experienceYears;
    private String education;
    private String bio;
    private String approach;
    private List<String> languages;
}