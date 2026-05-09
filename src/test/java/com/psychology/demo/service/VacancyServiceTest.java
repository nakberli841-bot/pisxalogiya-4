package com.psychology.demo.service;


import com.psychology.demo.dto.VacancyApplicationRequestDTO;
import com.psychology.demo.dto.VacancyDTO;
import com.psychology.demo.entity.*;
import com.psychology.demo.enums.CategoryForVacancy;
import com.psychology.demo.repo.UserRepository;
import com.psychology.demo.repository.VacancyApplicationRepository;
import com.psychology.demo.repository.VacancyCategoryRepository;
import com.psychology.demo.repository.VacancyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacancyServiceTest {

    @Mock
    private VacancyRepository vacancyRepository;
    @Mock
    private VacancyCategoryRepository categoryRepository;
    @Mock
    private VacancyApplicationRepository applicationRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VacancyService vacancyService;

    private Vacancy testVacancy;
    private VacancyCategory testCategory;
    private User testUser;

    @BeforeEach
    void setUp() {
        testCategory = VacancyCategory.builder().id(1L).name(CategoryForVacancy.CLINICAL).build();
        testVacancy = Vacancy.builder().id(10L).title("Psixoloq").vacancyCategory(testCategory).build();
        testUser = User.builder().id(5L).email("user@test.com").build();
    }

    @Test
    void createVacancy_Success() {
        VacancyDTO dto = VacancyDTO.builder().title("Psixoloq").categoryName(CategoryForVacancy.CLINICAL).build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(vacancyRepository.save(any(Vacancy.class))).thenReturn(testVacancy);

        VacancyDTO result = vacancyService.createVacancy(dto);

        assertNotNull(result);
        assertEquals("Psixoloq", result.getTitle());
        assertEquals("Klinik Psixologiya", result.getCategoryName());
        verify(vacancyRepository).save(any(Vacancy.class));
    }

    @Test
    void applyToVacancy_Success() {
        Long userId = 5L;
        VacancyApplicationRequestDTO request = new VacancyApplicationRequestDTO();
        request.setVacancyId(10L);
        request.setCoverLetter("Maraqlanıram");

        when(applicationRepository.existsByUserIdAndVacancyId(userId, 10L)).thenReturn(false);
        when(vacancyRepository.findById(10L)).thenReturn(Optional.of(testVacancy));
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        String response = vacancyService.applyToVacancy(request, userId);

        assertEquals("Müraciətiniz uğurla göndərildi!", response);
        verify(applicationRepository).save(any());
    }

    @Test
    void applyToVacancy_AlreadyApplied_ThrowsException() {
        Long userId = 5L;
        VacancyApplicationRequestDTO request = new VacancyApplicationRequestDTO();
        request.setVacancyId(10L);

        when(applicationRepository.existsByUserIdAndVacancyId(userId, 10L)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            vacancyService.applyToVacancy(request, userId);
        });

        assertEquals("Siz artıq bu vakansiyaya müraciət etmisiniz!", exception.getMessage());
        verify(applicationRepository, never()).save(any());
    }
}
