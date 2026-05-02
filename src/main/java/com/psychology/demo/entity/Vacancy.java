package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    private String location;
    private String salaryRange;

    private boolean isActive = true;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL)
    private List<JobApplication> applications;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}