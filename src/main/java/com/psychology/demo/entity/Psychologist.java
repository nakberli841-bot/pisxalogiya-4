package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "psychologists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Psychologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String specialty;
    private Integer experienceYears;
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String approach;

    private String imagePath;


    @ElementCollection
    @CollectionTable(name = "psychologist_languages", joinColumns = @JoinColumn(name = "psychologist_id"))
    @Column(name = "language")
    private List<String> languages;

    @OneToMany(mappedBy = "psychologist", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}