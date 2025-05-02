package com.macadev.advet.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewUpdateRequestDto {

    @NotBlank(message = "Feedback cannot be blank")
    private String feedback;

    @Min(value = 1, message = "Stars must be at least 1")
    @Max(value = 5, message = "Stars must be at most 5")
    private Integer stars; // Use Integer to allow null check if needed, or int if always required
}
