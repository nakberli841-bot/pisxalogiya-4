package com.psychology.demo.dto;

import com.psychology.demo.enums.CategoryForBlog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategoryDTO {
    private Long id;
    private CategoryForBlog name;
    private String description;
}
