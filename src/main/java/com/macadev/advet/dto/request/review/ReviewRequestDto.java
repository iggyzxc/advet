package com.macadev.advet.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDto {
    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @NotNull(message = "Veterinarian ID cannot be null")
    private Long veterinarianId;

    @NotBlank(message = "Feedback cannot be blank")
    private String feedback;

    @NotNull(message = "Star rating cannot be null")
    @Min(value = 1, message = "Stars must be at least 1")
    @Max(value = 5, message = "Stars must be at most 5")
    private Integer stars; // Use Integer if null is possible, int if always required
}
