package com.macadev.advet.service.review;

import com.macadev.advet.dto.request.review.ReviewRequestDto;
import com.macadev.advet.dto.response.ReviewDto;
import com.macadev.advet.dto.request.review.ReviewUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface IReviewService {
    ReviewDto createReview(ReviewRequestDto request);
    Page<ReviewDto> getAllReviewsByUserId(Long userId, int page, int size);
    double getAverageRating(Long veterinarianId);
    ReviewDto updateReview(Long reviewId, ReviewUpdateRequestDto request);
    void deleteReview(Long reviewId);
}
