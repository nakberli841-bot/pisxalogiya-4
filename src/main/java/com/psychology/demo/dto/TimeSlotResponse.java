package com.psychology.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSlotResponse {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
