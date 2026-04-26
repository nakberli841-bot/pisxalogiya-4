package com.psychology.demo.service;

import com.psychology.demo.dto.ContactRequestDTO;
import com.psychology.demo.dto.VacancyResponseDTO;
import com.psychology.demo.entity.ContactMessage;
import com.psychology.demo.repo.ContactMessageRepository;
import com.psychology.demo.repo.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralService {
    private final VacancyRepository vacancyRepository;
    private final ContactMessageRepository contactRepository;

    public void saveContactMessage(ContactRequestDTO request) {
        ContactMessage message = new ContactMessage();
        message.setSenderName(request.getSenderName());
        message.setSenderEmail(request.getSenderEmail());
        message.setMessage(request.getMessage());
        contactRepository.save(message);
    }

    public List<VacancyResponseDTO> getActiveVacancies() {
        return vacancyRepository.findAll().stream()
                .map(v -> {
                    VacancyResponseDTO dto = new VacancyResponseDTO();
                    dto.setId(v.getId());
                    dto.setPosition(v.getPosition());
                    return dto;
                }).collect(Collectors.toList());
    }
}