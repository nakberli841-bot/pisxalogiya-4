package com.psychology.demo.controller;

import com.psychology.demo.dto.PsychologicalTestDTO;
import com.psychology.demo.dto.TestResultResponse;
import com.psychology.demo.dto.TestSubmissionRequest;
import com.psychology.demo.entity.PsychologicalTest;
import com.psychology.demo.service.PsychologicalTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class PsychologicalTestController {

    private final PsychologicalTestService testService;

    @GetMapping("/{id}")
    public ResponseEntity<PsychologicalTestDTO> getTest(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestDetails(id));
    }

    @PostMapping("/submit")
    public ResponseEntity<TestResultResponse> submit(@RequestBody TestSubmissionRequest request) {
        return ResponseEntity.ok(testService.submitTest(request));
    }
}