package com.example.practice1.web.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentRequest(
        @NotBlank String uniId,
        @NotBlank String fullName
) {
}