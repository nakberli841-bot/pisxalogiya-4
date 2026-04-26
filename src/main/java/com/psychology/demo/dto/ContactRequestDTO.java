package com.psychology.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactRequestDTO {
    @NotBlank(message = "Adınız mütləqdir")
    private String senderName;
    @Email(message = "Düzgün email daxil edin")
    private String senderEmail;
    @NotBlank(message = "Mesaj hissəsi boş qala bilməz")
    @Size(min = 10, message = "Mesaj ən azı 10 simvoldan ibarət olmalıdır")
    private String message;
}