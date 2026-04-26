package com.psychology.demo.dto;

import lombok.Data;

@Data
public class VacancyResponseDTO {
    private Long id;
    private String position;
    private String location;
    private String type;
}