package com.example.practice1.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentRequest(
        @NotBlank String uniId,
        @NotBlank String fullName
) {
}