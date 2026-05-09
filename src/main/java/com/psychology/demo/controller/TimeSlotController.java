package com.psychology.demo.controller;

import com.psychology.demo.dto.TestResultResponse;
import com.psychology.demo.dto.TimeSlotCreateRequest;
import com.psychology.demo.dto.TimeSlotResponse;
import com.psychology.demo.entity.TimeSlot;
import com.psychology.demo.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;


    @PostMapping("/create")
    public ResponseEntity<TimeSlotResponse> addSlot(@RequestBody TimeSlotCreateRequest timeSlot) {
        TimeSlotResponse slot = timeSlotService.createSlot(timeSlot);
        return ResponseEntity.ok(slot);
    }

//bu istifadeciye secilen pisxologun uygun oldugu zaman intervalini verir eger randuvu zamani zaman aralliqi sececekse fronted
//terfden bu apiye muraciet olunsun bu abi userin randuvuda secdiyi pisxologun bos zamn vaxtlarini verecek
    @GetMapping("/available")
    public ResponseEntity<List<TimeSlot>> getAvailable() {
        return ResponseEntity.ok(timeSlotService.getAvailableSlots());
    }
}