package com.psychology.demo.dto;

import com.psychology.demo.enums.ApplicationStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyApplicationResponseDTO {
    private Long id;
    private String vacancyTitle;
    private String companyName;
    private LocalDateTime appliedAt;
    private ApplicationStatus status;
    private String coverLetter;
}