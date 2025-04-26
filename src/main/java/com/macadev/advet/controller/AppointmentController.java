package com.macadev.advet.controller;

import com.macadev.advet.dto.request.AppointmentUpdateRequest;
import com.macadev.advet.dto.response.ApiResponse;
import com.macadev.advet.dto.response.AppointmentDto;
import com.macadev.advet.enums.ResourceType;
import com.macadev.advet.model.Appointment;
import com.macadev.advet.service.appointment.AppointmentService;
import com.macadev.advet.util.FeedbackMessage;
import com.macadev.advet.util.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.APPOINTMENTS_BASE) // "/api/v1/appointments"
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentDto>> bookAppointment(
            @RequestParam Long patientId,
            @RequestParam Long veterinarianId,
            @RequestBody Appointment appointment) {
        AppointmentDto appointmentCreated = appointmentService.createAppointment(patientId, veterinarianId, appointment);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.Appointment), appointmentCreated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(UrlMapping.APPOINTMENT_ID_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentUpdateRequest request) {
        AppointmentDto updatedAppointment = appointmentService.updateAppointment(appointmentId, request);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.updateSuccess(ResourceType.Appointment), updatedAppointment);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentDto>>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        ApiResponse<List<AppointmentDto>> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.Appointment), appointments);
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.APPOINTMENT_ID_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentById(@PathVariable Long appointmentId) {
        AppointmentDto appointment = appointmentService.getAppointmentById(appointmentId);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.Appointment), appointment);
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.APPOINTMENT_NUMBER_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentByAppointmentNumber(@PathVariable String appointmentNumber) {
        AppointmentDto appointment = appointmentService.getByAppointmentNumber(appointmentNumber);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.Appointment), appointment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.APPOINTMENT_ID_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> deleteAppointmentById(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                FeedbackMessage.deleteSuccess(ResourceType.Appointment), null);
        return ResponseEntity.ok(response);
    }
}
