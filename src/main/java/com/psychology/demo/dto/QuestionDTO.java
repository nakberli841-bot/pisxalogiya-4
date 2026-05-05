package com.psychology.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QuestionDTO {
    private Long id;
    private String questionText;
    private List<AnswerOptionDTO> options;
}
