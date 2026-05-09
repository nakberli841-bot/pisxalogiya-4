package com.psychology.demo.controller;

import com.psychology.demo.dto.BlogPostDTO;
import com.psychology.demo.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;


    @PostMapping("/{categoryId}")
    public ResponseEntity<BlogPostDTO> createPost(@RequestBody BlogPostDTO blogPostDTO) {
        return ResponseEntity.ok(blogService.createPost(blogPostDTO));
    }


    @GetMapping
    public ResponseEntity<List<BlogPostDTO>> getAllPosts() {
        return ResponseEntity.ok(blogService.getAllPosts());
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BlogPostDTO>> getPostsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(blogService.getPostsByCategory(categoryId));
    }
}