package com.psychology.demo.service;


import com.psychology.demo.dto.BlogCategoryDTO;
import com.psychology.demo.entity.BlogCategory;
import com.psychology.demo.repo.BlogCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final BlogCategoryRepository blogCategoryRepository;


    public BlogCategoryDTO createCategory(BlogCategoryDTO dto) {
        BlogCategory blogCategory = BlogCategory.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        BlogCategory savedBlogCategory = blogCategoryRepository.save(blogCategory);

        return BlogCategoryDTO.builder()
                .id(savedBlogCategory.getId())
                .name(savedBlogCategory.getName())
                .description(savedBlogCategory.getDescription())
                .build();
    }


    public List<BlogCategoryDTO> getAllCategories() {
        return blogCategoryRepository.findAll().stream()
                .map(category -> BlogCategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build())
                .toList();
    }
}
