package com.psychology.demo.controller;

import com.psychology.demo.dto.BlogCategoryDTO;
import com.psychology.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BlogCategoryDTO> createCategory(@RequestBody BlogCategoryDTO blogCategoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(blogCategoryDTO));
    }


    @GetMapping
    public ResponseEntity<List<BlogCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
