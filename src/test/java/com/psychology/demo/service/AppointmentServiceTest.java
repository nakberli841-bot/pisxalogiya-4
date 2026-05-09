package com.psychology.demo.service;


import com.psychology.demo.dto.AppointmentRequestDTO;
import com.psychology.demo.entity.Appointment;
import com.psychology.demo.entity.TimeSlot;
import com.psychology.demo.entity.User;
import com.psychology.demo.excception.BusinessLogicException;
import com.psychology.demo.repo.AppointmentRepository;
import com.psychology.demo.repo.TimeSlotRepository;
import com.psychology.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private TimeSlotRepository timeSlotRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AppointmentService appointmentService;

    private User testUser;
    private TimeSlot testSlot;
    private Appointment testAppointment;

    @BeforeEach
    void setUp() {

        testUser = User.builder().id(1L).email("test@example.com").build();
        testSlot = TimeSlot.builder().id(10L).isBooked(false).build();
        testAppointment = new Appointment();


        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createAppointment_Success() {
        AppointmentRequestDTO requestDTO = new AppointmentRequestDTO();
        requestDTO.setCustomerFullName("Nurlan");
        requestDTO.setCustomerEmail("test@example.com");
        requestDTO.setCustomerPhone("+9940000000");
        requestDTO.setPsychologistId(1L);

        Long slotId = 10L;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(timeSlotRepository.findById(slotId)).thenReturn(Optional.of(testSlot));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);
        Appointment result = appointmentService.createAppointment(requestDTO, slotId);
        assertNotNull(result);
        assertTrue(testSlot.isBooked(), "Slot booked statusu true olmalı idi");
        verify(timeSlotRepository, times(1)).save(testSlot);

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
    @Test
    void createAppointment_ShouldThrowException_WhenSlotAlreadyBooked() {
        AppointmentRequestDTO requestDTO = new AppointmentRequestDTO();
        requestDTO.setCustomerEmail("test@example.com");

        Long slotId = 10L;
        testSlot.setBooked(true);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(timeSlotRepository.findById(slotId)).thenReturn(Optional.of(testSlot));

        BusinessLogicException exception = assertThrows(BusinessLogicException.class, () -> {
            appointmentService.createAppointment(requestDTO, slotId);
        });

        assertEquals("Bu vaxt artıq başqa bir istifadəçi tərəfindən rezerv edilib.", exception.getMessage());

        verify(timeSlotRepository, never()).save(any());
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void cancelAppointment_Success() {

        Long appointmentId = 1L;
        testAppointment.setTimeSlot(testSlot);
        testSlot.setBooked(true);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(testAppointment));

        appointmentService.cancelAppointment(appointmentId);


        assertFalse(testSlot.isBooked()); // Status false-a düşməlidir
        assertEquals("CANCELLED", testAppointment.getStatus());
        verify(timeSlotRepository).save(testSlot);
        verify(appointmentRepository).save(testAppointment);
    }
}
