package com.psychology.demo.service;


import com.psychology.demo.dto.CategoryDTO;
import com.psychology.demo.entity.Category;
import com.psychology.demo.repo.CategoryRepository;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;
    private CategoryDTO testDto;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .id(1L)
                .name("Klinik Psixologiya")
                .description("Bu kateqoriya klinik sahəni əhatə edir")
                .build();

        testDto = CategoryDTO.builder()
                .name("Klinik Psixologiya")
                .description("Bu kateqoriya klinik sahəni əhatə edir")
                .build();
    }

    @Test
    void createCategory_Success() {

        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);


        CategoryDTO result = categoryService.createCategory(testDto);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(testDto.getName(), result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void getAllCategories_Success() {

        when(categoryRepository.findAll()).thenReturn(List.of(testCategory));


        List<CategoryDTO> results = categoryService.getAllCategories();


        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Klinik Psixologiya", results.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_EmptyList() {

        when(categoryRepository.findAll()).thenReturn(List.of());


        List<CategoryDTO> results = categoryService.getAllCategories();


        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }
}