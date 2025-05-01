package com.macadev.advet.service.appointment;

import com.macadev.advet.dto.request.appointment.AppointmentRequestDto;
import com.macadev.advet.dto.request.appointment.AppointmentUpdateRequestDto;
import com.macadev.advet.dto.response.AppointmentDto;

import java.util.List;

public interface IAppointmentService {

    AppointmentDto createAppointment(AppointmentRequestDto request);
    List<AppointmentDto> getAllAppointments();
    AppointmentDto getAppointmentById(Long appointmentId);
    AppointmentDto getByAppointmentNumber(String appointmentNumber);
    AppointmentDto updateAppointment(Long appointmentId, AppointmentUpdateRequestDto request);
    void deleteAppointmentById(Long appointmentId);
}
