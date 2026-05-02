package com.psychology.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "bos ola bilmez")
    private String fullName;

    @Email(message = "email formayinda olmalidir")
    private String email;

    @Size(min=8,message = "8 simvoldan az ola bilmez")
    private String password;
}