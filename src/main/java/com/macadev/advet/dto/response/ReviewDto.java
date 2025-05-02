package com.macadev.advet.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {

    private Long id;

    @NotNull(message = "Patient ID cannot be null")
    private Long patient_id;

    @NotNull(message = "Veterinarian ID cannot be null")
    private Long veterinarian_id;

    @NotNull(message = "Review cannot be null")
    private String feedback;

    @NotNull(message = "Star rating cannot be null")
    @Min(value = 1, message = "Stars must be at least 1")
    @Max(value = 5, message = "Stars must be at most 5")
    private Integer stars;

    private LocalDateTime createdAt;
}
