package com.psychology.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PsychologistDetailDTO extends PsychologistResponseDTO {
    private String education;
    private String bio;
    private String approach;
    private List<String> certificates;
}
