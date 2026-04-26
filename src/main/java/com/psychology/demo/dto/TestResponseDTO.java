package com.psychology.demo.dto;

import lombok.Data;

@Data
public class TestResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private Integer questionCount;
}