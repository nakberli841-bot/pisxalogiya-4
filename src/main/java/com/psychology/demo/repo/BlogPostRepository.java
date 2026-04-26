package com.psychology.demo.repo;

import com.psychology.demo.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Page<BlogPost> findByCategory(String category, Pageable pageable);

    List<BlogPost> findByTitleContainingIgnoreCase(String title);
}
