package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "test_result_ranges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minScore;
    private Integer maxScore;

    private String riskLevel;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    @ManyToOne
    @JoinColumn(name = "psychological_test_id")
    private PsychologicalTest psychologicalTest;
}