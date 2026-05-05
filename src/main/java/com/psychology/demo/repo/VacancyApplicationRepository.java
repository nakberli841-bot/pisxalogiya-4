package com.psychology.demo.repository;

import com.psychology.demo.entity.VacancyApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VacancyApplicationRepository extends JpaRepository<VacancyApplication, Long> {

    List<VacancyApplication> findByUserId(Long userId);


    List<VacancyApplication> findByVacancyId(Long vacancyId);

    boolean existsByUserIdAndVacancyId(Long userId, Long vacancyId);
}