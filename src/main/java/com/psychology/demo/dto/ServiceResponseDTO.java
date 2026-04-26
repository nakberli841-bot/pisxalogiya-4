package com.psychology.demo.dto;

import lombok.Data;

@Data
public class ServiceResponseDTO {
    private Long id;
    private String title;
    private String sessionFormat;
    private Double price;
    private String icon;
}
