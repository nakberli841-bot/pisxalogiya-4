package com.psychology.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestSubmissionRequest {
    private Long testId;
    private List<Long> selectedOptionIds;
}
