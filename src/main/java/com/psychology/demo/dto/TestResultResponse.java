package com.psychology.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultResponse {
    private Integer totalScore;
    private String riskLevel;
    private String recommendation;
}