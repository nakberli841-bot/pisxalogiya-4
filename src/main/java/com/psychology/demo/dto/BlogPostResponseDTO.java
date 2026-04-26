package com.psychology.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogPostResponseDTO {
    private Long id;
    private String title;
    private String category;
    private String author;
    private LocalDateTime publishDate;
    private Integer readingTime;
    private String thumbnail;
}