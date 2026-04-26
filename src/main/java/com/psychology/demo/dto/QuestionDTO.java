package com.psychology.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class QuestionDTO {
    private String questionText;
    private Map<String, Integer> options;
}