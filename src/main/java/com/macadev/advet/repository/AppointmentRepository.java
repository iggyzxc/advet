package com.macadev.advet.repository;

import com.macadev.advet.enums.AppointmentStatus;
import com.macadev.advet.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentNumber(String appointmentNumber);

    // Check if a completed appointment exists for this patient/vet combination
    boolean existsByPatientIdAndVeterinarianIdAndStatus(Long patientId, Long veterinarianId, AppointmentStatus status);
}
