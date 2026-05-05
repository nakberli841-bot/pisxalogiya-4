package com.psychology.demo.service;


import com.psychology.demo.dto.BlogPostDTO;
import com.psychology.demo.entity.BlogPost;
import com.psychology.demo.entity.Category;
import com.psychology.demo.repo.BlogPostRepository;
import com.psychology.demo.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogPostRepository blogPostRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BlogPostDTO createPost(BlogPostDTO dto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Kateqoriya tapılmadı"));


        BlogPost post = BlogPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();


        BlogPost savedPost = blogPostRepository.save(post);


        return BlogPostDTO.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .imageUrl(savedPost.getImageUrl())
                .createdAt(savedPost.getCreatedAt())
                .categoryName(savedPost.getCategory().getName())
                .build();
    }

    public List<BlogPostDTO> getAllPosts() {
        return blogPostRepository.findAll().stream()
                .map(post -> BlogPostDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .categoryName(post.getCategory().getName()) // Kateqoriya adını buradan alırıq
                        .build())
                .toList();
    }

    public List<BlogPostDTO> getPostsByCategory(Long categoryId) {
        return blogPostRepository.findByCategoryId(categoryId).stream()
                .map(post -> BlogPostDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .categoryName(post.getCategory().getName())
                        .build())
                .toList();
    }
}