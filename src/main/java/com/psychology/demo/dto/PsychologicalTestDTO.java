package com.psychology.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PsychologicalTestDTO {
    private Long id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private List<QuestionDTO> questions;
}
