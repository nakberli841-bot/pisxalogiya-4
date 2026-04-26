package com.psychology.demo.controller;

import com.psychology.demo.entity.User;
import com.psychology.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/transfer-ownership/{doctorId}")
    public ResponseEntity<String> transfer(@PathVariable Long doctorId) {
        User currentAdmin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        adminService.transferOwnershipToDoctor(doctorId, currentAdmin.getId());

        return ResponseEntity.ok("İdarəçilik uğurla həkimə ötürüldü. Siz artıq admin deyilsiniz.");
    }
}