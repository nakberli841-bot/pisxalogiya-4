package com.psychology.demo.service;

import com.psychology.demo.dto.VacancyDTO;
import com.psychology.demo.dto.VacancyApplicationRequestDTO;
import com.psychology.demo.entity.*;
import com.psychology.demo.enums.ApplicationStatus;
import com.psychology.demo.excception.BusinessLogicException;
import com.psychology.demo.excception.ResourceNotFoundException;
import com.psychology.demo.repo.UserRepository;
import com.psychology.demo.repository.VacancyApplicationRepository;
import com.psychology.demo.repository.VacancyCategoryRepository;
import com.psychology.demo.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyCategoryRepository categoryRepository;
    private final VacancyApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;



    @Transactional
    public VacancyDTO createVacancy(VacancyDTO dto) {
        VacancyCategory category = categoryRepository.findByName(dto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("category not found"));

        Vacancy vacancy = Vacancy.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .companyName(dto.getCompanyName())
                .location(dto.getLocation())
                .salaryRange(dto.getSalaryRange())
                .workType(dto.getWorkType())
                .deadline(dto.getDeadline())
                .vacancyCategory(category)
                .build();

        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return convertToVacancyDTO(savedVacancy);
    }

    public List<VacancyDTO> getAllVacancies() {
        return vacancyRepository.findAll().stream()
                .map(this::convertToVacancyDTO)
                .toList();
    }

    public List<VacancyDTO> getVacanciesByCategory(Long categoryId) {
        return vacancyRepository.findByVacancyCategoryId(categoryId).stream()
                .map(this::convertToVacancyDTO)
                .toList();
    }



    @Transactional
    public String applyToVacancy(VacancyApplicationRequestDTO dto, Long userId) {


        boolean alreadyApplied = applicationRepository.existsByUserIdAndVacancyId(userId, dto.getVacancyId());

        if (alreadyApplied) {
            throw new BusinessLogicException("Siz artıq bu vakansiyaya müraciət etmisiniz!");
        }

        Vacancy vacancy = vacancyRepository.findById(dto.getVacancyId())
                .orElseThrow(() -> new ResourceNotFoundException("Vakansiya tapılmadı!"));


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("İstifadəçi tapılmadı!"));


        VacancyApplication application = VacancyApplication.builder()
                .vacancy(vacancy)
                .user(user)
                .coverLetter(dto.getCoverLetter())
                .cvFilePath(dto.getCvFilePath()) // PDF yolu bura yazılır
                .status(ApplicationStatus.PENDING)
                .build();

        applicationRepository.save(application);

        return "Müraciətiniz uğurla göndərildi!";
    }


    private VacancyDTO convertToVacancyDTO(Vacancy vacancy) {
        return VacancyDTO.builder()
                .id(vacancy.getId())
                .title(vacancy.getTitle())
                .description(vacancy.getDescription())
                .companyName(vacancy.getCompanyName())
                .location(vacancy.getLocation())
                .salaryRange(vacancy.getSalaryRange())
                .workType(vacancy.getWorkType())
                .deadline(vacancy.getDeadline())
                .categoryName(vacancy.getVacancyCategory().getName())
                .categoryName(vacancy.getVacancyCategory().getName())
                .build();
    }
}