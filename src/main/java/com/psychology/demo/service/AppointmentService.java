package com.psychology.demo.service;

import com.psychology.demo.dto.AppointmentRequestDTO;
import com.psychology.demo.dto.AppointmentResponseDTO;
import com.psychology.demo.entity.Appointment;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.repo.AppointmentRepository;
import com.psychology.demo.repo.PsychologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PsychologistRepository psychologistRepository;

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO request) {
        Psychologist psychologist = psychologistRepository.findById(request.getPsychologistId())
                .orElseThrow(() -> new RuntimeException("Psixoloq tapılmadı"));

        Appointment appointment = new Appointment();
        appointment.setPsychologist(psychologist);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setCustomerFullName(request.getCustomerFullName());
        appointment.setCustomerEmail(request.getCustomerEmail());
        appointment.setStatus("PENDING");

        Appointment saved = appointmentRepository.save(appointment);

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setId(saved.getId());
        response.setPsychologistName(psychologist.getFirstName());
        response.setStatus(saved.getStatus());
        return response;
    }
}