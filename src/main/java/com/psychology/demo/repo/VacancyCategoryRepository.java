package com.psychology.demo.repository;

import com.psychology.demo.entity.VacancyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyCategoryRepository extends JpaRepository<VacancyCategory, Long> {
}