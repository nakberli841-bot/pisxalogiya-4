package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "psychologists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Psychologist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String specialty;
    private Integer experienceYears;
    private String language;
    private Double rating;
    private String education;
    private String bio;
    private String approach;
    private String imagePath;

    @OneToMany(mappedBy = "psychologist")
    private List<Appointment> appointments;
}
