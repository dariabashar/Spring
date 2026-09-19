package com.example.practice1.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.id")
public record AppIdProperties(
        @NotBlank String greeting,
        @Min(1) @Max(20) int minIdLength,
        @Min(1) @Max(20) int maxIdLength
) {
}