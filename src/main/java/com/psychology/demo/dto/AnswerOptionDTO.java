package com.psychology.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerOptionDTO {
    private Long id;
    private String optionText;
}
