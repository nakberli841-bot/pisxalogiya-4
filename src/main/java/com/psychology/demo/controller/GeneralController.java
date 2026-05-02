//package com.psychology.demo.controller;
//
//import com.psychology.demo.dto.ContactRequestDTO;
//import com.psychology.demo.dto.VacancyResponseDTO;
//import com.psychology.demo.service.GeneralService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/general")
//@RequiredArgsConstructor
//public class GeneralController {
//    private final GeneralService generalService;
//
//    @PostMapping("/contact")
//    public ResponseEntity<String> sendMessage(@RequestBody ContactRequestDTO request) {
//        generalService.saveContactMessage(request);
//        return ResponseEntity.ok("Mesajınız uğurla göndərildi!");
//    }
//
//    @GetMapping("/vacancies")
//    public ResponseEntity<List<VacancyResponseDTO>> getVacancies() {
//        return ResponseEntity.ok(generalService.getActiveVacancies());
//    }
//}