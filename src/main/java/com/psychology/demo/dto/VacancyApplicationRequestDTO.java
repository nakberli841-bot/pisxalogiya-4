package com.psychology.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyApplicationRequestDTO {
    private Long vacancyId;
    private String coverLetter;

    private String cvFilePath;
}