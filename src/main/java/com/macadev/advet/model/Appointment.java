package com.macadev.advet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.macadev.advet.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
@JsonIgnoreProperties({"patient", "veterinarian"})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private  String appointmentNumber; // Appointment reference number

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

   @JoinColumn(name = "patient_id")
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for performance since we only want to load the user when needed
    private User patient;

   @JoinColumn(name = "veterinarian_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User veterinarian;

   public void addPatient(User patient) {
        this.setPatient(patient);
        if (patient.getAppointments() == null) {
            patient.setAppointments(new ArrayList<>());
        }
        patient.getAppointments().add(this);
    }

    public void addVeterinarian(User veterinarian) {
        this.setVeterinarian(veterinarian);
        if (veterinarian.getAppointments() == null) {
            veterinarian.setAppointments(new ArrayList<>());
        }
    }

    public void setAppointmentNumber() {
        this.appointmentNumber = String.valueOf(new Random().nextLong()).substring(1, 11);
    }
}
