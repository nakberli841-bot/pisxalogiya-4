package com.psychology.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Long id;
    private String psychologistName;
    private LocalDateTime appointmentDate;
    private String status;
}