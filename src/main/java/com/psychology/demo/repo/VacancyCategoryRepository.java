package com.psychology.demo.repository;

import com.psychology.demo.entity.BlogCategory;
import com.psychology.demo.entity.VacancyCategory;
import com.psychology.demo.enums.CategoryForBlog;
import com.psychology.demo.enums.CategoryForVacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacancyCategoryRepository extends JpaRepository<VacancyCategory, Long> {
    Optional<VacancyCategory> findByName(CategoryForVacancy name);

}