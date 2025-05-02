package com.macadev.advet.service.review;

import com.macadev.advet.dto.request.review.ReviewRequestDto;
import com.macadev.advet.dto.request.review.ReviewUpdateRequestDto;
import com.macadev.advet.dto.response.ReviewDto;
import com.macadev.advet.enums.AppointmentStatus;
import com.macadev.advet.enums.ResourceType;
import com.macadev.advet.exception.InvalidInputException;
import com.macadev.advet.exception.ResourceNotFoundException;
import com.macadev.advet.model.Review;
import com.macadev.advet.model.User;
import com.macadev.advet.repository.AppointmentRepository;
import com.macadev.advet.repository.ReviewRepository;
import com.macadev.advet.repository.UserRepository;
import com.macadev.advet.util.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    @Override
    @Transactional
    public ReviewDto createReview(ReviewRequestDto request) {
        log.debug("Saving review from patient ID {} for vet ID {}", request.getPatientId(), request.getVeterinarianId());
        // Fetch related entities
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient User", request.getPatientId()));
        User veterinarian = userRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian User", request.getVeterinarianId()));

        // Validation checks
        // Prevent self-review
        if (Objects.equals(patient.getId(), veterinarian.getId())) {
            log.warn("Patient and Veterinarian cannot review themselves");
            throw new InvalidInputException("Patient and Veterinarian cannot review themselves");
        }

        // Prevent duplicate reviews
        if (reviewRepository.existsByPatientIdAndVeterinarianId(patient.getId(), veterinarian.getId())) {
            log.warn("Duplicate review attempt by patient ID {} for veterinarian ID {}", request.getPatientId(), request.getVeterinarianId());
            throw new InvalidInputException("You have already submitted a review for this veterinarian");
        }

        // Ensure completed appointment exists
        boolean hadCompletedAppointment = appointmentRepository.existsByPatientIdAndVeterinarianIdAndStatus(
                patient.getId(),
                veterinarian.getId(),
                AppointmentStatus.COMPLETED);
        if (!hadCompletedAppointment) {
            log.warn("No completed appointment found for patient ID {} and veterinarian ID {}", request.getPatientId(), request.getVeterinarianId());
            throw new InvalidInputException("You must have a completed appointment with this veterinarian to leave a review");
        }

        // Map DTO to entity
        Review review = modelMapper.map(request, Review.class);

        // Set relationships
        review.setPatient(patient);
        review.setVeterinarian(veterinarian);

        Review savedReview = reviewRepository.save(review);
        log.info("Review saved with ID: {}", savedReview.getId());

        // Return DTO
        return modelMapper.map(savedReview, ReviewDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getAllReviewsByUserId(Long userId, int page, int size) {
        log.debug("Fetching reviews for user ID {} - Page: {}, Size: {}", userId, page, size);
        PageRequest pageRequest = PageRequest.of(page, size);

        return reviewRepository.findAllByUserId(userId, pageRequest)
                .map(review -> modelMapper.map(review, ReviewDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public double getAverageRating(Long veterinarianId) {
        log.debug("Calculating average rating for veterinarian ID: {}", veterinarianId);
        List<Review> reviews = reviewRepository.findByVeterinarianId(veterinarianId);
        double average = reviews.isEmpty() ? 0 : reviews.stream()
                .mapToInt(Review::getStars) // method reference to get stars
                .average()
                .orElse(0); // Return double 0.0
        log.debug("Average rating for vet ID {} is {}", veterinarianId, average);
        return average;
    }

    @Override
    @Transactional
    public ReviewDto updateReview(Long reviewId, ReviewUpdateRequestDto request) {
        log.debug("Attempting to update review with ID: {}", reviewId);

        // Find the existing review by its ID
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.warn("Review not found for update with ID: {}", reviewId);
                    return new ResourceNotFoundException(FeedbackMessage.noResourceFound(ResourceType.REVIEW), reviewId);
                });

        // Todo: Check if the review belongs to the user


        modelMapper.map(request, existingReview);
        log.debug("Applied updates from DTO to Review entity with ID: {}", reviewId);
        Review savedReview = reviewRepository.save(existingReview);

        // Map saved entity to response DTO
        return modelMapper.map(savedReview, ReviewDto.class);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        log.debug("Attempting to delete review with ID: {}", reviewId);
        // TODO: check to ensure the user deleting is the original user
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(
                        review -> {
                            reviewRepository.delete(review);
                            log.info("Review with ID {} deleted successfully", reviewId);
                        },
                        () -> {
                            log.warn("Review not found for deletion with ID: {}", reviewId);
                            throw new ResourceNotFoundException(FeedbackMessage.noResourceFound(ResourceType.REVIEW), reviewId);
                        }
                );
    }
}
