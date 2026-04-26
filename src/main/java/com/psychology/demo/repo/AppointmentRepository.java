package com.psychology.demo.repo;

import com.psychology.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPsychologistIdAndAppointmentDateBetween(
            Long psychologistId, LocalDateTime start, LocalDateTime end);
}