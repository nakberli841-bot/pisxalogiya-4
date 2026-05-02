package com.psychology.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @Email
    private String email;

    @NotBlank(message = "bos ola bilmez")
    @Size(min = 8,message = "8 simvoldan az ola bilmez")
    private String password;
}