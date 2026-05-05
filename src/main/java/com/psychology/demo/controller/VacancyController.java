package com.psychology.demo.controller;

import com.psychology.demo.dto.VacancyDTO;
import com.psychology.demo.dto.VacancyApplicationRequestDTO;
import com.psychology.demo.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;


    @PostMapping("/create")
    public ResponseEntity<VacancyDTO> createVacancy(@RequestBody VacancyDTO dto) {
        return ResponseEntity.ok(vacancyService.createVacancy(dto));
    }


    @GetMapping
    public ResponseEntity<List<VacancyDTO>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAllVacancies());
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<VacancyDTO>> getVacanciesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(categoryId));
    }


    @PostMapping("/apply")
    public ResponseEntity<String> applyToVacancy(@RequestBody VacancyApplicationRequestDTO dto,
                                                 @RequestParam Long userId) {
        return ResponseEntity.ok(vacancyService.applyToVacancy(dto, userId));
    }
}