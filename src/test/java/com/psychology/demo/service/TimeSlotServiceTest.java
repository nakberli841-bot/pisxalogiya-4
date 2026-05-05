package com.psychology.demo.service;


import com.psychology.demo.dto.TimeSlotCreateRequest;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.entity.TimeSlot;
import com.psychology.demo.repo.PsychologistRepository;
import com.psychology.demo.repo.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeSlotServiceTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private PsychologistRepository psychologistRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TimeSlotService timeSlotService;

    private Psychologist testPsychologist;
    private final String email = "psychologist@test.com";

    @BeforeEach
    void setUp() {
        testPsychologist = Psychologist.builder()
                .id(1L)
                .specialty("Klinik")
                .build();

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createSlot_Success() {
        TimeSlotCreateRequest request = new TimeSlotCreateRequest();
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));

        TimeSlot savedSlot = TimeSlot.builder()
                .id(100L)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .psychologist(testPsychologist)
                .isBooked(false)
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(psychologistRepository.findByUserEmail(email)).thenReturn(Optional.of(testPsychologist));
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(savedSlot);

        TimeSlot result = timeSlotService.createSlot(request);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertFalse(result.isBooked());
        assertEquals(testPsychologist, result.getPsychologist());
        verify(timeSlotRepository, times(1)).save(any(TimeSlot.class));
    }

    @Test
    void createSlot_PsychologistNotFound_ThrowsException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(psychologistRepository.findByUserEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            timeSlotService.createSlot(new TimeSlotCreateRequest());
        });

        verify(timeSlotRepository, never()).save(any());
    }

    @Test
    void getAvailableSlots_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(psychologistRepository.findByUserEmail(email)).thenReturn(Optional.of(testPsychologist));

        TimeSlot slot1 = TimeSlot.builder().id(1L).isBooked(false).build();
        when(timeSlotRepository.findAllByPsychologistIdAndIsBookedFalse(1L)).thenReturn(List.of(slot1));


        List<TimeSlot> results = timeSlotService.getAvailableSlots();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isBooked());
        verify(timeSlotRepository).findAllByPsychologistIdAndIsBookedFalse(1L);
    }
}