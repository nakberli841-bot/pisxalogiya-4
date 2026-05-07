package com.psychology.demo.repo;

import com.psychology.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c " +
            "LEFT JOIN FETCH c.posts " +
            "WHERE c.id = :categoryId")
    Optional<Category> findCategoryWithPosts(@Param("categoryId") Long categoryId);
}