package com.deepak.auth.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    @NotBlank(message = "Username must not be null")
    private String userName;

    private String fullName;

    @NotBlank(message = "Email Id must not be null")
    @Email(message = "Please provide a valid email address")
    private String emailId;


    @NotBlank(message = "Password must not be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
