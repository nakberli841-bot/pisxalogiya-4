package com.psychology.demo.controller;

import com.psychology.demo.dto.AppointmentRequestDTO;
import com.psychology.demo.dto.AppointmentResponseDTO;
import com.psychology.demo.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDTO> book(@Valid @RequestBody AppointmentRequestDTO request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }
}