package com.psychology.demo.dto;

import com.psychology.demo.enums.CategoryForBlog;
import com.psychology.demo.enums.CategoryForVacancy;
import lombok.Data;

@Data
public class VacancyCategoryDTO {

    private Long id;
    private CategoryForVacancy name;
    private String description;
}
