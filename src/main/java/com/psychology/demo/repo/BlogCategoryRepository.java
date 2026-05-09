package com.psychology.demo.repo;

import com.psychology.demo.entity.BlogCategory;
import com.psychology.demo.enums.CategoryForBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {

    Optional<BlogCategory> findByName(CategoryForBlog name);
}