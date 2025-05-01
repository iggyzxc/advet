package com.macadev.advet.dto.request.appointment;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentUpdateRequestDto {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
}
