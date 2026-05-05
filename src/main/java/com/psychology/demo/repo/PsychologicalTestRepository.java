package com.psychology.demo.repo;

import com.psychology.demo.entity.PsychologicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PsychologicalTestRepository extends JpaRepository<PsychologicalTest, Long> {
    @Query("SELECT t FROM PsychologicalTest t " +
            "LEFT JOIN FETCH t.questions q " +
            "LEFT JOIN FETCH q.options " +
            "WHERE t.id = :testId")
    Optional<PsychologicalTest> findFullTestDetails(@Param("testId") Long testId);
}