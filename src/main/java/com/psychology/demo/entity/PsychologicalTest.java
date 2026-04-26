package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "psychological_tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PsychologicalTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer durationMinutes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;
}