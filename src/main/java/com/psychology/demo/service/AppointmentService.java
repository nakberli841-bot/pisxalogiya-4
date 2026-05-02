package com.psychology.demo.service;

import com.psychology.demo.entity.Appointment;
import com.psychology.demo.entity.TimeSlot;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.AppointmentRepository;
import com.psychology.demo.repo.TimeSlotRepository;
import com.psychology.demo.repo.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;


    @Transactional
    public Appointment createAppointment(Appointment appointment, Long timeSlotId) {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("İstifadəçi tapılmadı!"));

        TimeSlot slot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Seçilmiş vaxt tapılmadı!"));


        if (slot.isBooked()) {
            throw new RuntimeException("Bu vaxt artıq başqa bir istifadəçi tərəfindən rezerv edilib.");
        }

        slot.setBooked(true);
        timeSlotRepository.save(slot);

        appointment.setTimeSlot(slot);
        appointment.setUser(user);
        return appointmentRepository.save(appointment);
    }


    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Görüş tapılmadı!"));


        TimeSlot slot = appointment.getTimeSlot();
        slot.setBooked(false);
        timeSlotRepository.save(slot);

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }
}