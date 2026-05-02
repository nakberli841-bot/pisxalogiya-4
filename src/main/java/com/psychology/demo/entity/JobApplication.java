package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hansı vakansiya üçün müraciət edilib?
    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String applicantFullName;
    private String applicantEmail;
    private String applicantPhone;

    private String cvFilePath;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;
    private String status;

    private LocalDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }
}