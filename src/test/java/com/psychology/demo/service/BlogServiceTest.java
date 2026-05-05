package com.psychology.demo.service;

import com.psychology.demo.dto.BlogPostDTO;
import com.psychology.demo.entity.BlogPost;
import com.psychology.demo.entity.Category;
import com.psychology.demo.repo.BlogPostRepository;
import com.psychology.demo.repo.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BlogService blogService;

    private Category testCategory;
    private BlogPost testPost;
    private BlogPostDTO testDto;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .id(1L)
                .name("Psixologiya")
                .build();

        testPost = BlogPost.builder()
                .id(100L)
                .title("Test Başlıq")
                .content("Test Məzmun")
                .imageUrl("image.jpg")
                .category(testCategory)
                .createdAt(LocalDateTime.now())
                .build();

        testDto = BlogPostDTO.builder()
                .title("Test Başlıq")
                .content("Test Məzmun")
                .imageUrl("image.jpg")
                .build();
    }

    @Test
    void createPost_Success() {

        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(testCategory));
        when(blogPostRepository.save(any(BlogPost.class))).thenReturn(testPost);


        BlogPostDTO result = blogService.createPost(testDto, categoryId);


        assertNotNull(result);
        assertEquals(testPost.getTitle(), result.getTitle());
        assertEquals("Psixologiya", result.getCategoryName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(blogPostRepository, times(1)).save(any(BlogPost.class));
    }

    @Test
    void createPost_ShouldThrowException_WhenCategoryNotFound() {

        Long categoryId = 99L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            blogService.createPost(testDto, categoryId);
        });

        assertEquals("Kateqoriya tapılmadı", exception.getMessage());
        verify(blogPostRepository, never()).save(any());
    }

    @Test
    void getPostsByCategory_Success() {

        Long categoryId = 1L;
        when(blogPostRepository.findByCategoryId(categoryId)).thenReturn(java.util.List.of(testPost));

        java.util.List<BlogPostDTO> results = blogService.getPostsByCategory(categoryId);


        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("Psixologiya", results.get(0).getCategoryName());
    }
}
