package com.macadev.advet.service.appointment;

import com.macadev.advet.dto.request.AppointmentUpdateRequest;
import com.macadev.advet.dto.response.AppointmentDto;
import com.macadev.advet.model.Appointment;

import java.util.List;

public interface IAppointmentService {

    AppointmentDto createAppointment(Long patientId, Long veterinarianId, Appointment appointment);
    AppointmentDto updateAppointment(Long id, AppointmentUpdateRequest request);
    List<AppointmentDto> getAllAppointments();
    AppointmentDto getAppointmentById(Long id);
    AppointmentDto getByAppointmentNumber(String appointmentNumber);
    void deleteAppointmentById(Long id);
}
