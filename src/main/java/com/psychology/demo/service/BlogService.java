package com.psychology.demo.service;

import com.psychology.demo.dto.BlogPostResponseDTO;
import com.psychology.demo.entity.BlogPost;
import com.psychology.demo.repo.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogPostRepository blogPostRepository;

    public Page<BlogPostResponseDTO> getBlogPosts(String category, Pageable pageable) {
        Page<BlogPost> posts;
        if (category != null) {
            posts = blogPostRepository.findByCategory(category, pageable);
        } else {
            posts = blogPostRepository.findAll(pageable);
        }
        return posts.map(post -> {
            BlogPostResponseDTO dto = new BlogPostResponseDTO();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setCategory(post.getCategory());
            return dto;
        });
    }
}