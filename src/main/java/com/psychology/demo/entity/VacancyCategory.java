package com.psychology.demo.entity;

import com.psychology.demo.enums.CategoryForVacancy;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryForVacancy name;

    private String description;


    @OneToMany(mappedBy = "vacancyCategory", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;
}