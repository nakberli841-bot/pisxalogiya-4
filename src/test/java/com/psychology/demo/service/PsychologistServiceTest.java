package com.psychology.demo.service;


import com.psychology.demo.dto.PsychologistProfileRequest;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.PsychologistRepository;
import com.psychology.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PsychologistServiceTest {

    @Mock
    private PsychologistRepository psychologistRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private PsychologistService psychologistService;

    private User testUser;
    private Psychologist testPsychologist;
    private PsychologistProfileRequest testRequest;
    private final String email = "psychologist@example.com";

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email(email)
                .fullName("Dr. Nurlan")
                .build();

        testPsychologist = Psychologist.builder()
                .id(10L)
                .user(testUser)
                .specialty("Klinik")
                .build();

        testRequest = new PsychologistProfileRequest();
        testRequest.setSpecialty("Terapevt");
        testRequest.setExperienceYears(5);
        testRequest.setLanguages(List.of("Azərbaycan", "İngilis"));
    }

    @Test
    void updateOrCreateProfile_UpdateExisting_Success() {
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(psychologistRepo.findByUserEmail(email)).thenReturn(Optional.of(testPsychologist));

        String response = psychologistService.updateOrCreateProfile(testRequest, email);

        assertEquals("Profil uğurla yeniləndi", response);
        assertEquals("Terapevt", testPsychologist.getSpecialty()); // Obyektin daxili yenilənib
        verify(psychologistRepo).save(testPsychologist);
    }

    @Test
    void updateOrCreateProfile_CreateNew_Success() {
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(psychologistRepo.findByUserEmail(email)).thenReturn(Optional.empty());

               String response = psychologistService.updateOrCreateProfile(testRequest, email);

        assertEquals("Profil uğurla yeniləndi", response);
        verify(psychologistRepo).save(any(Psychologist.class));
    }

    @Test
    void updateOrCreateProfile_UserNotFound_ThrowsException() {
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            psychologistService.updateOrCreateProfile(testRequest, email);
        });

        assertEquals("İstifadəçi tapılmadı", exception.getMessage());
        verify(psychologistRepo, never()).save(any());
    }

    @Test
    void getMyProfile_Success() {
        when(psychologistRepo.findByUserEmail(email)).thenReturn(Optional.of(testPsychologist));

        Psychologist result = psychologistService.getMyProfile(email);

        assertNotNull(result);
        assertEquals("Klinik", result.getSpecialty());
        verify(psychologistRepo).findByUserEmail(email);
    }

    @Test
    void getMyProfile_NotFound_ThrowsException() {

        when(psychologistRepo.findByUserEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            psychologistService.getMyProfile(email);
        });

        assertEquals("Profil hələ yaradılmayıb", exception.getMessage());
    }
}
