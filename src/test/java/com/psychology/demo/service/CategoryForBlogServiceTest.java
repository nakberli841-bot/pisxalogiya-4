package com.psychology.demo.service;


import com.psychology.demo.dto.BlogCategoryDTO;
import com.psychology.demo.entity.BlogCategory;
import com.psychology.demo.enums.CategoryForBlog;
import com.psychology.demo.repo.BlogCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryForBlogServiceTest {

    @Mock
    private BlogCategoryRepository blogCategoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private BlogCategory testBlogCategory;
    private BlogCategoryDTO testDto;

    @BeforeEach
    void setUp() {
        testBlogCategory = BlogCategory.builder()
                .id(1L)
                .name(CategoryForBlog.CLINICAL)
                .description("Bu kateqoriya klinik sahəni əhatə edir")
                .build();

        testDto = BlogCategoryDTO.builder()
                .name(CategoryForBlog.CLINICAL)
                .description("Bu kateqoriya klinik sahəni əhatə edir")
                .build();
    }

    @Test
    void createCategory_Success() {

        when(blogCategoryRepository.save(any(BlogCategory.class))).thenReturn(testBlogCategory);


        BlogCategoryDTO result = categoryService.createCategory(testDto);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(testDto.getName(), result.getName());
        verify(blogCategoryRepository, times(1)).save(any(BlogCategory.class));
    }

    @Test
    void getAllCategories_Success() {

        when(blogCategoryRepository.findAll()).thenReturn(List.of(testBlogCategory));


        List<BlogCategoryDTO> results = categoryService.getAllCategories();


        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Klinik Psixologiya", results.get(0).getName());
        verify(blogCategoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_EmptyList() {

        when(blogCategoryRepository.findAll()).thenReturn(List.of());


        List<BlogCategoryDTO> results = categoryService.getAllCategories();


        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }
}