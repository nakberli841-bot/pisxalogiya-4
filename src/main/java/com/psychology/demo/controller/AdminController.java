package com.psychology.demo.controller;

import com.psychology.demo.dto.RegisterRequest;
import com.psychology.demo.entity.User;
import com.psychology.demo.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PutMapping("/transfer-ownership/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")//bu annotasiya securty filtirde bele filtr qoymasaqda yalnis admin olanlar ucun isleyecek
    //or istfade ederek bu annotasiya ile bir nece ferqli rolun eyni anda grisini temin ede bilersen
    public ResponseEntity<String> transfer(
            @PathVariable Long doctorId,
            @AuthenticationPrincipal User currentAdmin
    ) {
        if (currentAdmin.getId().equals(doctorId)) {
            return ResponseEntity.badRequest().body("Siz artıq adminsiniz!");
        }

        adminService.transferOwnershipToDoctor(doctorId, currentAdmin.getId());

        return ResponseEntity.ok("Səlahiyyətlər uğurla həkimə (ID: " + doctorId + ") ötürüldü.");
    }


    @PostMapping("/registrDocWithAdmin")
    public ResponseEntity<Map<String,String>> registrDocWithAdmin(@Valid @RequestBody RegisterRequest request){
       return ResponseEntity.ok(adminService.registrDocWithAdmin(request));
}}