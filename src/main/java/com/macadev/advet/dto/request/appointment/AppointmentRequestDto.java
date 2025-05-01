package com.macadev.advet.dto.request.appointment;

import com.macadev.advet.dto.request.pet.PetRequestDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class AppointmentRequestDto {
    private Long patientId;
    private Long veterinarianId;
    private List<PetRequestDto> pets;
    private String reason;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
}
