package com.psychology.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AppointmentRequestDTO {

    @NotNull(message = "Psixoloq seçilməlidir")
    private Long psychologistId;

    @NotNull(message = "Vaxt intervalı (TimeSlot) seçilməlidir")
    private Long timeSlotId;

    @NotBlank(message = "Ad və soyad boş ola bilməz")
    @Size(min = 3, max = 50, message = "Ad və soyad 3-50 simvol arası olmalıdır")
    private String customerFullName;

    @NotBlank(message = "Telefon nömrəsi boş ola bilməz")
    private String customerPhone;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email formatı düzgün deyil")
    private String customerEmail;


}