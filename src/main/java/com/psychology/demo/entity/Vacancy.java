package com.psychology.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;       // İşin adı (məs: Klinik Psixoloq)

    @Column(columnDefinition = "TEXT")
    private String description; // İş barədə ətraflı məlumat

    private String companyName; // Klinika və ya şirkət adı
    private String location;    // Ünvan
    private String salaryRange; // Maaş aralığı (məs: 800-1200 AZN)
    private String workType;    // İş rejimi (Tam iş günü, Uzaqdan və s.)

    private LocalDateTime deadline;  // Son müraciət tarixi
    private LocalDateTime createdAt; // Elanın paylaşıldığı tarix

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private VacancyCategory vacancyCategory; // Vakansiyanın aid olduğu sahə (məs: Uşaq Psixologiyası)

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}