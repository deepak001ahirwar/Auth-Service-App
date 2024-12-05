package com.deepak.auth.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "NOT NULL")
    private String userName;

    @NotNull(message = "NOT NULL")
    private String password;
}
