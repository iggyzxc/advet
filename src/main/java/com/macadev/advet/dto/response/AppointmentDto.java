package com.macadev.advet.dto.response;

import com.macadev.advet.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentDto {
    private Long id;
    private String reason;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalDateTime createdAt;
    private String appointmentNumber;
    private AppointmentStatus status;
}
