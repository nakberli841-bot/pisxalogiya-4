package com.psychology.demo.controller;

import com.psychology.demo.dto.AppointmentRequestDTO;
import com.psychology.demo.entity.Appointment;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentRequestDTO requestDTO) {

        appointmentService.createAppointment(requestDTO, requestDTO.getTimeSlotId());

        return ResponseEntity.ok("ugurla yaradildi");
    }


}