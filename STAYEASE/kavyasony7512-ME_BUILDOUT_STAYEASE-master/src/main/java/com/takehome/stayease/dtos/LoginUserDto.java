package com.takehome.stayease.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @Email(message = "Invalid mail format")
    @NotBlank(message = "mail cannot be empty")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;

}
