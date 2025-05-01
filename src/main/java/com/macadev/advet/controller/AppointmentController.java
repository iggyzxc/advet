package com.macadev.advet.controller;

import com.macadev.advet.dto.request.appointment.AppointmentRequestDto;
import com.macadev.advet.dto.request.appointment.AppointmentUpdateRequestDto;
import com.macadev.advet.dto.response.ApiResponse;
import com.macadev.advet.dto.response.AppointmentDto;
import com.macadev.advet.enums.ResourceType;
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
    public ResponseEntity<ApiResponse<AppointmentDto>> createAppointment(@RequestBody AppointmentRequestDto appointment) {
        AppointmentDto appointmentCreated = appointmentService.createAppointment(appointment);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.APPOINTMENT), appointmentCreated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentDto>>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        ApiResponse<List<AppointmentDto>> response;
        if (appointments.isEmpty()) {
            // Returning an empty list doesn't mean there is an error, so we return 200 OK with an empty list
            response = new ApiResponse<>(FeedbackMessage.noResourceFound(ResourceType.APPOINTMENT), null);
        } else {
            response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.APPOINTMENT), appointments);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.APPOINTMENT_ID_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentById(@PathVariable Long appointmentId) {
        AppointmentDto appointment = appointmentService.getAppointmentById(appointmentId);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.APPOINTMENT), appointment);
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.APPOINTMENT_NUMBER_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentByAppointmentNumber(@PathVariable String appointmentNumber) {
        AppointmentDto appointment = appointmentService.getByAppointmentNumber(appointmentNumber);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.APPOINTMENT), appointment);
        return ResponseEntity.ok(response);
    }

    @PutMapping(UrlMapping.APPOINTMENT_ID_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentUpdateRequestDto request) {
        AppointmentDto updatedAppointment = appointmentService.updateAppointment(appointmentId, request);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(FeedbackMessage.updateSuccess(ResourceType.APPOINTMENT), updatedAppointment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.APPOINTMENT_ID_VARIABLE)
    public ResponseEntity<ApiResponse<AppointmentDto>> deleteAppointmentById(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        ApiResponse<AppointmentDto> response = new ApiResponse<>(
                FeedbackMessage.deleteSuccess(ResourceType.APPOINTMENT), null);
        return ResponseEntity.ok(response);
    }
}
