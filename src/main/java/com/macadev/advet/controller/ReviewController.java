package com.macadev.advet.controller;

import com.macadev.advet.dto.request.review.ReviewRequestDto;
import com.macadev.advet.dto.request.review.ReviewUpdateRequestDto;
import com.macadev.advet.dto.response.ApiResponse;
import com.macadev.advet.dto.response.ReviewDto;
import com.macadev.advet.enums.ResourceType;
import com.macadev.advet.model.Review;
import com.macadev.advet.service.review.IReviewService;
import com.macadev.advet.util.FeedbackMessage;
import com.macadev.advet.util.UrlMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.REVIEWS_BASE)
public class ReviewController {

    private final IReviewService reviewService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDto>> createReview(
            @Valid @RequestBody ReviewRequestDto request) { // Use @Valid for DTO validation
        ReviewDto createdReview = reviewService.createReview(request);
        ApiResponse<ReviewDto> response = new ApiResponse<>(
                FeedbackMessage.createSuccess(ResourceType.REVIEW),
                createdReview);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}") // Example path: /api/v1/reviews/user/5
    public ResponseEntity<ApiResponse<Page<ReviewDto>>> getAllReviewsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Service returns Page<Review> (entity)
        Page<ReviewDto> reviewPage = reviewService.getAllReviewsByUserId(userId, page, size);

        ApiResponse<Page<ReviewDto>> response;
        if (reviewPage.isEmpty()) {
            response = new ApiResponse<>(FeedbackMessage.noResourceFound(ResourceType.REVIEW), null);
        } else {
            response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.REVIEW), reviewPage);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vet/{veterinarianId}/rating") // Example path: /api/v1/reviews/vet/10/rating
    public ResponseEntity<ApiResponse<Double>> getAverageRating(@PathVariable Long veterinarianId) {
        double averageRating = reviewService.getAverageRating(veterinarianId);

        ApiResponse<Double> response = new ApiResponse<>(
                String.format("Average rating for veterinarian %d calculated.", veterinarianId), // Custom message
                averageRating);
        return ResponseEntity.ok(response);
    }

    @PutMapping(UrlMapping.REVIEW_ID_VARIABLE)
    public ResponseEntity<ApiResponse<ReviewDto>> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequestDto request) { // Use @Valid
        ReviewDto updatedReview = reviewService.updateReview(reviewId, request);
        ApiResponse<ReviewDto> response = new ApiResponse<>(
                FeedbackMessage.updateSuccess(ResourceType.REVIEW),
                updatedReview);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.REVIEW_ID_VARIABLE)
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

}
