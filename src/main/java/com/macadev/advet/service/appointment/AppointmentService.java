package com.macadev.advet.service.appointment;

import com.macadev.advet.dto.request.AppointmentUpdateRequest;
import com.macadev.advet.dto.response.AppointmentDto;
import com.macadev.advet.enums.AppointmentStatus;
import com.macadev.advet.exception.ResourceNotFoundException;
import com.macadev.advet.model.Appointment;
import com.macadev.advet.model.User;
import com.macadev.advet.repository.AppointmentRepository;
import com.macadev.advet.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentDto createAppointment(Long patientId, Long veterinarianId, Appointment appointment) {
        Optional<User> patient = userRepository.findById(patientId);
        Optional<User> veterinarian = userRepository.findById(veterinarianId);
        if (patient.isPresent() && veterinarian.isPresent()) {
            // set both patient and vet to appointment with a safety check
            appointment.addPatient(patient.get());
            appointment.addVeterinarian(veterinarian.get());

            appointment.setAppointmentNumber();
            appointment.setStatus(AppointmentStatus.PENDING);
            return modelMapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
        }
        throw new ResourceNotFoundException("Appointment", appointment);
    }

    @Override
    @Transactional
    public AppointmentDto updateAppointment(Long appointmentId, AppointmentUpdateRequest request) {
        AppointmentDto appointmentDto = getAppointmentById(appointmentId);
        // if the appointment is not pending, it cannot be updated
        if (!Objects.equals(appointmentDto.getStatus(), AppointmentStatus.PENDING)) {
            throw new IllegalStateException("Sorry, this appointment can no longer be updated");
        }
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        modelMapper.map(request, appointment);
        appointmentRepository.save(appointment);
        AppointmentDto updatedAppointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        return modelMapper.map(updatedAppointmentDto, AppointmentDto.class);
    }

    @Override
    @Transactional
    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        return appointmentList.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .toList();
    }

    @Override
    @Transactional
    public AppointmentDto getAppointmentById(Long appointmentId) {
        Appointment appointment =  appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", appointmentId));
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public AppointmentDto getByAppointmentNumber(String appointmentNumber) {
        Appointment appointment = appointmentRepository.findByAppointmentNumber(appointmentNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", appointmentNumber));
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public void deleteAppointmentById(Long appointmentId) {
        appointmentRepository.findById(appointmentId).ifPresentOrElse(
                appointmentRepository::delete,
                () -> {throw new ResourceNotFoundException("Appointment", appointmentId);
        });
    }
}
