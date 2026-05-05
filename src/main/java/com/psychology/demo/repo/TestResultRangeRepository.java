package com.psychology.demo.repo;

import com.psychology.demo.entity.TestResultRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestResultRangeRepository extends JpaRepository<TestResultRange, Long> {

    @Query("SELECT r FROM TestResultRange r WHERE r.psychologicalTest.id = :testId " +
            "AND :score BETWEEN r.minScore AND r.maxScore")
    Optional<TestResultRange> findByPsychologicalTestIdAndScoreBetween(Long testId, int score);
}