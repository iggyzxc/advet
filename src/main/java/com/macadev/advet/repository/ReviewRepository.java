package com.macadev.advet.repository;

import com.macadev.advet.model.Review;
import com.macadev.advet.service.review.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.patient.id = :userId OR r.veterinarian.id = :userId")
    Page<Review> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    List<Review> findByVeterinarianId(Long veterinarianId);

    // Check if a review already exists for this patient/vet combination
    boolean existsByPatientIdAndVeterinarianId(Long patientId, Long veterinarianId);
}
