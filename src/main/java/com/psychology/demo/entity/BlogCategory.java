package com.psychology.demo.entity;

import com.psychology.demo.enums.CategoryForBlog;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryForBlog name;

    private String description;


    @OneToMany(mappedBy = "blogCategory", cascade = CascadeType.ALL)
    private List<BlogPost> posts;
}