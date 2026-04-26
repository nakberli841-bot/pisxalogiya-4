package com.psychology.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    @NotNull(message = "Psixoloq seçilməlidir")
    private Long psychologistId;
    @NotNull(message = "Görüş tarixi təyin edilməlidir")
    @Future(message = "Görüş tarixi keçmiş zaman ola bilməz")
    private LocalDateTime appointmentDate;
    @NotBlank(message = "Ad və soyad boş ola bilməz")
    @Size(min = 3, max = 50, message = "Ad və soyad 3-50 simvol arası olmalıdır")
    private String customerFullName;
    private String customerPhone;
    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email formatı düzgün deyil")
    private String customerEmail;
}