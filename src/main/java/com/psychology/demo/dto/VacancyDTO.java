package com.psychology.demo.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDTO {
    private Long id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private String salaryRange;
    private String workType;
    private LocalDateTime deadline;
    private Long categoryId;
    private String categoryName;
}