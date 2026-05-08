package com.acm.seguridad.seguridad_avanzada_acm.payload;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
