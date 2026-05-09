package com.psychology.demo.service;


import com.psychology.demo.dto.BlogPostDTO;
import com.psychology.demo.entity.BlogPost;
import com.psychology.demo.entity.BlogCategory;
import com.psychology.demo.excception.ResourceNotFoundException;
import com.psychology.demo.repo.BlogPostRepository;
import com.psychology.demo.repo.BlogCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogPostRepository blogPostRepository;
    private final BlogCategoryRepository blogCategoryRepository;

    @Transactional
    public BlogPostDTO createPost(BlogPostDTO dto) {

        BlogCategory blogCategory = blogCategoryRepository.findByName(dto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));


        BlogPost post = BlogPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .blogCategory(blogCategory)
                .build();


        BlogPost savedPost = blogPostRepository.save(post);


        return BlogPostDTO.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .imageUrl(savedPost.getImageUrl())
                .createdAt(savedPost.getCreatedAt())
                .categoryName(savedPost.getBlogCategory().getName())
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
                        .categoryName(post.getBlogCategory().getName()) // Kateqoriya adını buradan alırıq
                        .build())
                .toList();
    }

    public List<BlogPostDTO> getPostsByCategory(Long categoryId) {
        return blogPostRepository.findByBlogCategory_Id(categoryId).stream()
                .map(post -> BlogPostDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .categoryName(post.getBlogCategory().getName())
                        .build())
                .toList();
    }
}