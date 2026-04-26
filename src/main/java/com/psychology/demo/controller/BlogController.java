package com.psychology.demo.controller;

import com.psychology.demo.dto.BlogPostResponseDTO;
import com.psychology.demo.entity.ServiceEntity;
import com.psychology.demo.repo.ServiceRepository;
import com.psychology.demo.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<BlogPostResponseDTO>> getPosts(
            @RequestParam(required = false) String category,
            Pageable pageable) {
        return ResponseEntity.ok(blogService.getBlogPosts(category, pageable));
    }
}

